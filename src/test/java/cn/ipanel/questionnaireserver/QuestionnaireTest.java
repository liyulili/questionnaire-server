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
                .setBody("[{\"code\": \"A\",\"content\": \"紫色\",\"desc\": \"\",\"pic\": \"http://xxxxxx\"},{\"code\": \"B\",\"content\": \"绿色\",\"desc\": \"\",\"pic\": \"http://xxxxxx\"},{\"code\": \"C\",\"content\": \"蓝色\",\"desc\": \"\",\"pic\": \"http://xxxxxx\"}]")
                .setQuestionBank(1)
                .setQuestionType(1)
                .setRequired(1)
                .setTitle("你最喜欢哪种颜色");
        int insert = questionMapper.insert(question);
        System.out.println("insert = " + insert);
        System.out.println("id=" + question.getId());
    }

}
