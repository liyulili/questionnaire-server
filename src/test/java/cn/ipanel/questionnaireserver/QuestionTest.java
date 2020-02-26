package cn.ipanel.questionnaireserver;


import cn.ipanel.questionnaireserver.vo.QuestionBody;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QuestionTest {

    /**
     * 测试question_body字段解析
     */
    @Test
    public void testQuestionBody(){

        String JsonStr ="[\n" +
                "  {\n" +
                "    \"code\": \"A\",\n" +
                "    \"content\": \"上海\",\n" +
                "    \"desc\": \"XXXXXX\",\n" +
                "    \"pic\": \"http://192.168.2.3/XX\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"code\": \"A\",\n" +
                "    \"content\": \"上海\",\n" +
                "    \"desc\": \"XXXXXX\",\n" +
                "    \"pic\": \"http://192.168.2.3/XX\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"code\": \"A\",\n" +
                "    \"content\": \"上海\",\n" +
                "    \"desc\": \"XXXXXX\",\n" +
                "    \"pic\": \"http://192.168.2.3/XX\"\n" +
                "  }\n" +
                "]";

        List<QuestionBody> questionBodies = JSON.parseArray(JsonStr, QuestionBody.class);
        questionBodies.forEach(q->System.out.println(q.toString()));

    }

}
