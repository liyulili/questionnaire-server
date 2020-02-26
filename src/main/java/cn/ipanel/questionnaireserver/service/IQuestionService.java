package cn.ipanel.questionnaireserver.service;

import cn.ipanel.questionnaireserver.pojo.Question;
import cn.ipanel.questionnaireserver.vo.R;

public interface IQuestionService {

    /**
     * 向题库添加问题
     * @param question
     * @return
     */
    R addQuestionAsBank(Question question);
}
