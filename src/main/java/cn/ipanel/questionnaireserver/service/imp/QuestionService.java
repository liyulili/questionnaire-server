package cn.ipanel.questionnaireserver.service.imp;

import cn.ipanel.questionnaireserver.mapper.QuestionMapper;
import cn.ipanel.questionnaireserver.pojo.Question;
import cn.ipanel.questionnaireserver.service.IQuestionService;
import cn.ipanel.questionnaireserver.constant.QuestionConstant;
import cn.ipanel.questionnaireserver.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService implements IQuestionService {

    @Autowired
    QuestionMapper questionMapper;


    @Transactional
    @Override
    public R addQuestionAsBank(Question question) {
        question.setId(null)
                .setQuestionBank(QuestionConstant.QUESTION_WITH_BANK);
        int insert = questionMapper.insert(question);
        if (insert!=1){
            return R.error("添加问题失败");
        }
        return R.ok();
    }
}
