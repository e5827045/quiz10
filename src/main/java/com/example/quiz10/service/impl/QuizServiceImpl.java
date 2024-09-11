package com.example.quiz10.service.impl;

import java.time.LocalDate;
import java.util.*;

import javax.transaction.Transactional;

import com.example.quiz10.service.ifs.QuizService;
import com.example.quiz10.vo.*;
import com.example.quiz10.constants.ResMessage;
import com.example.quiz10.constants.SelectType;
import com.example.quiz10.entity.Feedback;
import com.example.quiz10.entity.Question;
import com.example.quiz10.entity.Quiz;
import com.example.quiz10.repository.FeedbackDao;
import com.example.quiz10.repository.QuestionDao;
import com.example.quiz10.repository.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class QuizServiceImpl implements QuizService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private QuizDao quizDAO;
    @Autowired
    private QuestionDao questionDAO;
    private FeedbackDao feedbackDao;



    // @Transactional import 的 library， javax 和 springframework 都可以使用
    // 兩者差異可參照 PPT spring boot_02 @Transactional 部分
    @Transactional
    @Override
    public BasicRes create(CreateUpdateReq req) {

        // 基本的屬性判斷已交由@Valid
        // 開始時間不能比結束時間晚
        if (req.getStartDate().isAfter(req.getEndDate())) {
            return new BasicRes(ResMessage.DATE_ERROR.getCode(), ResMessage.DATE_ERROR.getMessage());
        }

        // 判斷問題型態非文字時，選項要有值
        List<Quiz> quizList = req.getQuizList();
        for (Quiz item : quizList) {
            if (item.getType().equalsIgnoreCase(SelectType.SINGLE.getType())
                    || item.getType().equalsIgnoreCase(SelectType.MULTI.getType())) {
                // 確定是單選或多選後，選項就必須要有值
                if (!StringUtils.hasText(item.getOptions())) {
                    return new BasicRes(ResMessage.OPTION_ERROR.getCode(), ResMessage.OPTION_ERROR.getMessage());
                }
            }
        }
        //因為Quiz中的id是AI產生的流水號，要讓quizDAO執行save後可以把該id的值回傳
        //必須要Quiz此Entity中將屬性id的資料型態設為 int
        //加上@GeneratedValue(strategy = GenerationType.IDENTITY)
        Question res = questionDAO.save(
                new Question(req.getName(), req.getDescription(), req.getStartDate(), req.getEndDate(), req.isPublished()));
        //將返回的res中的id(quiz_id)塞到Ques中的quizId此屬性(欄位)上
        quizList.forEach(item -> {
            item.setId(res.getId());
        });
        quizDAO.saveAll(quizList);

        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }

    @Override
    public BasicRes update(CreateUpdateReq req) {
        // 基本的屬性判斷已交由@Valid
        // 開始時間不能比結束時間晚
        if (req.getStartDate().isAfter(req.getEndDate())) {
            return new BasicRes(ResMessage.DATE_ERROR.getCode(), ResMessage.DATE_ERROR.getMessage());
        }

        // 判斷問題型態非文字時，選項要有值
        List<Quiz> questionList = req.getQuizList();
        for (Quiz item : questionList) {
            //檢查問題是否屬於此張問卷(檢查問題中的quizId和問卷中的id是否相同)
            if (item.getQuizId() != req.getId()) {
                return new BasicRes(ResMessage.QUIZ_ID_NOT_MATCH.getCode(), ResMessage.QUIZ_ID_NOT_MATCH.getMessage());
            }
            if (item.getType().equalsIgnoreCase(SelectType.SINGLE.getType())
                    || item.getType().equalsIgnoreCase(SelectType.MULTI.getType())) {
                // 確定是單選或多選後，選項就必須要有值
                if (!StringUtils.hasText(item.getOptions())) {
                    return new BasicRes(ResMessage.OPTION_ERROR.getCode(), ResMessage.OPTION_ERROR.getMessage());
                }
            }
        }

        //檢查要修改的問卷是否存在
        if (req.getId() <= 0 || !questionDAO.existsById(req.getId())) {
            return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
        }
        questionDAO.save(
                new Question(req.getId(), req.getName(), req.getDescription(), req.getStartDate(), req.getEndDate(), req.isPublished()));
        //刪除此張問卷所有的問題
        quizDAO.deleteByQuizId(req.getId());
        //新增更新後的問題
        quizDAO.saveAll(questionList);

        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());

    }


    @Override
    public SearchRes search(SearchRes req) {
        return null;
    }

    @Override
    public BasicRes delete(DeleteReq req) {
// "進行中"的問卷不能刪除：找出要刪除的 idList 中是否有包含進行中的問卷
        // 進行中的問卷條件：1.已發布 且 2.當前日期>=開始日期 且 3.當前日期<=結束日期
        boolean res = questionDAO.existsByIdInAndPublishedTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual( //
                req.getQuizIdList(), LocalDate.now(), LocalDate.now());
        if (res) { // res == true，表示要刪除的問卷 ID 中有正在進行中的
            return new BasicRes(ResMessage.QUIZ_IN_PROGRESS.getCode(), ResMessage.QUIZ_IN_PROGRESS.getMessage());
        }
//        quizDAO.deleteAllById(req.getQuizIdList());

        // 刪除選取的問卷
        questionDAO.deleteAllById(req.getQuizIdList());
        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());


        // 刪除選取的問卷e




    }



    @Override
    public SearchRes search(SearchReq req) {
        String quizName = req.getQuizName();
        LocalDate startDate = req.getStartDate();
        LocalDate endDate = req.getEndDate();
        if (!StringUtils.hasText(quizName)) {
            //quizName不帶值時則為非搜尋條件之一,所以其有可能會是空字串或null
            // ˊ全空白也當成是非搜尋條件之一
            //會將quizName變成空字串是因為containing的值帶空字串時會撈全部

            quizName = "";
        }
        if (startDate == null) {
            startDate = LocalDate.of(1970, 1, 5);

        }
        if (endDate == null) {
            endDate = LocalDate.of(2999, 12, 31);
        }

        List<Question> res = questionDAO.findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
                quizName, startDate, endDate

        );
        List<QuizRes> quizResList = new ArrayList<>();
        //方法1:使用對應quizId撈取問卷裡的quizList
        for (Question item : res) {
            //根據 quizId撈取每張問卷裡的 quizList
            int quizId = item.getId();
            List<Quiz> quizList = quizDAO.findById(quizId);
            //建立不同問卷的QuizRes用來放List<Quiz>
            //若參數太多建構方法容易混淆 建議使用以下方法
            QuizRes quizRes = new QuizRes();
            quizRes.setId(quizId);
            quizRes.setName(item.getName());
            quizRes.setDescription(item.getDescription());
            quizRes.setStartDate(item.getStartDate());
            quizRes.setEndDate(item.getEndDate());
            quizRes.setPublished(item.isPublished());
            quizRes.setQuizList(quizList);
            //把每張不同問卷和問題放到List<QuizRes>中
            quizResList.add(quizRes);
        }
        List<Integer> quizIdList = new ArrayList<>();
        for (Question item : res) {
            quizIdList.add(item.getId());
        }

        List<Quiz> quizResList2 = new ArrayList<>();
        //將符合搜尋條件的res(所有問卷)和 qesList(所有問題)配對
        for (Question item : res) {
            int quizId = item.getId();
            List<Quiz> returnQuizList = new ArrayList<>();
            for (Quiz quizItem : quizResList2) {

                if (quizId == quizItem.getQuizId()) {
                    returnQuizList.add(quizItem);

                }
            }

        }

        return new SearchRes(ResMessage.SUCCESS.getCode(),
                ResMessage.SUCCESS.getMessage(), quizResList);
    }


    @Override
    public BasicRes fillin(FillInReq req) {
        //檢查同一個email+quizId是否已存在(同一個Email已經填過同一張問卷)
        //quizId和email只會有其一
        List<Feedback> feedbackList = req.getFeebackList();
        Set<Integer> quizIdSet = new HashSet<>();
        Set<String> emailSet = new HashSet<>();
        String email;
        int quizId = 1;
        for (Feedback item : feedbackList) {

            quizIdSet.add(item.getQuizId());
            emailSet.add(item.getEmail());
        }

        //
        if (quizIdSet.size() != 1 || emailSet.size() != 1) {
            return new BasicRes(ResMessage.QUIZ_ID_OR_EMAIL_INCONSISTENT.getCode(),
                    ResMessage.QUIZ_ID_OR_EMAIL_INCONSISTENT.getMessage());
        }
        if (feedbackDao.existsByQuizIdAndEmail(req.getFeebackList().get(0).getQuizId(),
                req.getFeebackList().get(0).getEmail())) {
            return new BasicRes(ResMessage.EMAIL_DUPLICATED.getCode(),
                    ResMessage.EMAIL_DUPLICATED.getMessage());
        }
        //檢查問卷是否處於可填寫狀態:1.已發布
        //                      2.當前時間大於開始時間
        //                      3.當前時間小等於結束時間
        //前面有驚嘆浩表示找布道已發布且當前時間是介於開始時間與結束時間的資料
        if (!questionDAO.existsByIdInAndPublishedTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                List.of(req.getFeebackList().get(0).getQuizId()),
                LocalDate.now(), LocalDate.now())) {
            return new BasicRes(ResMessage.CANNOT_FILLIN_QUIZ.getCode(),
                    ResMessage.CANNOT_FILLIN_QUIZ.getMessage());
        }

        List<Quiz> quizList = quizDAO.findByQuizId(quizId);
        if (feedbackList.size() != quizList.size()) {
            return new BasicRes(ResMessage.FILLIN_INCOMPLETE.getCode()
                    , ResMessage.FILLIN_INCOMPLETE.getMessage());
        }
        Set<Integer> necessaryQuIds = new HashSet<>();
        for (Quiz quizItem : quizList) {
            Set<Integer> singleQuIds = new HashSet<>();

            if (quizItem.isNecessary()) {
                necessaryQuIds.add(quizItem.getId());

                if (quizItem.getType().equalsIgnoreCase(SelectType.SINGLE.getType())) {
                    singleQuIds.add(quizItem.getId());
                }
                Map<Integer, List<String>> quidAns = new HashMap<>();
                for (Feedback item : feedbackList) {
                    int quId = item.getQuId();

                    if (necessaryQuIds.contains(quId) && !StringUtils.hasText(item.getAns())) {
                        return new BasicRes(ResMessage.FILLIN_IN_IS_NECESSARY.getCode(),
                                ResMessage.QUIZ_ID_OR_EMAIL_INCONSISTENT.getMessage());
                    }

                    List<String> ansList = List.of(item.getAns().split(";"));
                    if (singleQuIds.contains(quId) && ansList.size() > 1) {
                        return new BasicRes(ResMessage.SINGLE_CHOICE_QUES.getCode(), ResMessage.
                                SINGLE_CHOICE_QUES.getMessage());

                    }

                    for (Quiz quiz : quizList) {
                        String type = quiz.getType();
                        if ((!type.equalsIgnoreCase(SelectType.TEXT.getType()))) {
                            List<String> optionList = List.of(quiz.getOptions().split(";"));


                        }


                    }


                }
                feedbackDao.saveAll(feedbackList);
            }

        }
        return null;
    }

    @Override
    public StatisticsRes statistics(int quizId) {
        //先撈取quiz的其他資訊
        Optional<Question> op = questionDAO.findById(quizId);
        if (op.isEmpty()) {
            return new StatisticsRes(ResMessage.QUIZ_NOT_FOUND.getCode(),
                    ResMessage.QUIZ_NOT_FOUND.getMessage());

        }
        Question question = op.get();
        String quizName = question.getName();
        //先ques撈取非文字類型的問題(文字類型的問題不列入統計)

        List<Quiz> quizList = quizDAO.findByQuizIdAndTypeNot(quizId, SelectType.TEXT.getType());
        //收集單選或多選問題的id，後續撈取feedback資料用
        List<Integer> quIdList = new ArrayList<>();
        //先蒐集問題的編號與選項，之後可確定feedback中的答案定會與選項中答案一致
        //    問題編號      選項      次數
        Map<Integer, Map<String, Integer>> quIdOptionCountMap = new HashMap<>();
        List<StatisticsVo> statisticsList = new ArrayList<>();
        for (Quiz item : quizList) {
            quIdList.add(item.getId());
            Map<String, Integer> optionCountMap = new HashMap<>();
            List<String> optionList = List.of(item.getOptions().split(";"));
            for (String option : optionList) {
                optionCountMap.put(option, 0);
            }
            quIdOptionCountMap.put(item.getId(), optionCountMap);
            StatisticsVo vo = new StatisticsVo();
            statisticsList.add(vo);
            vo.setQuId(item.getId());
            vo.setQu(item.getQu());
            statisticsList.add(vo);
        }

        //=====================================
        // 上面的 for 迴圈執行後類似以下概念
        //    題目編號     選項       次數
        // Map<Integer, Map<String, Integer>> quIdOptionCountMap = new HashMap<>();
        //          1        蛋餅            0
        //          1       三明治           0
        //          1       飯糰            0
        //          1       漢堡            0
        //          1       奶茶            0
        // 以上相同 key 的資料，只會有一筆就是題目編號1，但對應到的value 中的 map 會有 4 個資料
        // 可寫 test 資料得知資料格式
        //   2    奶茶  0
        // 以此類推
        //======================================
        List<Feedback> feedbackList = feedbackDao.findByQuizIdAndQuIdIn(quizId,quIdList);


        for (Feedback item : feedbackList) {
            //根據問題編號把選項次數的map從quIdOptionCountMap中取出
            Map<String, Integer> optionCountMap = quIdOptionCountMap.get(item.getQuId());
            //將feedback中的ans轉成List
            List<String> ansList = List.of(item.getAns().split(";"));
            for (String ans : ansList) {
                int count = optionCountMap.get(ans);
                //將原本的次數加1
                count++;
                //把相同的選項加次數放回map中
                optionCountMap.put(ans, count);

            }
        }


        for (Feedback item : feedbackList) {
            StatisticsVo vo = new StatisticsVo();
            Map<String, Integer> optionCountMap = new HashMap<>();
            List<String> ansList = List.of(item.getAns().split(";"));
            for (String ans : ansList) {
                //判斷map中已有選項key
                if (optionCountMap.containsKey(ans)) {
                    //選項已在map中將對應的次數取出
                    int count = optionCountMap.get(ans);
                    //將原本的次數加1
                    count++;
                    //把相同的選項加次數放回map中
                    optionCountMap.put(ans, count);

                }
            }
                quIdOptionCountMap.put(item.getQuId(), optionCountMap);
        }
            for (StatisticsVo item :statisticsList){
                int quId = item.getQuId();
                Map<String,Integer> optionCountMap = quIdOptionCountMap.get(quId);
                item.setOptionCountMap(optionCountMap);
                //上面三行程式碼可用下面一行表示
                //item.setOptionCountMap(quIdOptionCountMap.get(item.getQuid()));
            }


        return new StatisticsRes(ResMessage.SUCCESS.getCode(),
                ResMessage.SUCCESS.getMessage(),quizName,statisticsList);
    }

    @Override
    public FeedbackRes feedback(int quizId) {
       if(quizId<=0){
           return new  FeedbackRes(ResMessage.SUCCESS.getCode(),
                   ResMessage.SUCCESS.getMessage(),new  ArrayList<>());

       }
        List<Feedback> res = feedbackDao.findByQuizId(quizId);
       return new FeedbackRes(ResMessage.SUCCESS.getCode(),
               ResMessage.SUCCESS.getMessage(),res);
       }



}

