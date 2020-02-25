package cn.ipanel.questionnaireserver;

import cn.ipanel.questionnaireserver.mapper.QuestionMapper;
import cn.ipanel.questionnaireserver.pojo.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QuestionnaireTest {

    @Autowired
    QuestionMapper questionMapper;

    /**
     * 测试插入操作后主键是否回填
     */
    @Test
    public void testInsertQuestion() {
        Question question = new Question()
                .setId(null)
                .setBody("[{var:1},{var:2}]")
                .setQuestionBank(1)
                .setQuestionType(1)
                .setRequired(1)
                .setTitle("测试");
        int insert = questionMapper.insert(question);
        System.out.println("insert = " + insert);
        System.out.println("id=" + question.getId());
    }

}
