package com.lito.core.chat.adapter.out;

import com.lito.core.chat.domain.Chat;
import com.theokanning.openai.Usage;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@DataMongoTest
@ComponentScan(basePackages = "com.lito.core.chat.adapter")
@ExtendWith(SpringExtension.class)
@DisplayName("ChatCommandAdapter")
class ChatCommandAdapterTest {


    @MockBean
    OpenAiService openAiService;

    @Autowired
    ChatCommandAdapter chatCommandAdapter;

    @Autowired
    ChatRepository chatRepository;

    Chat chat = Chat.of(1L,1L,"질문","답변");

    @BeforeEach
    void setUp(){
        chatRepository.save(chat);
    }

    @AfterEach
    void tearDown(){
        chatRepository.deleteAll();
    }

    @Nested
    @DisplayName("save 메서드는")
    class save{

        @Nested
        @DisplayName("chat을 가지고")
        class with_chat{

            @Test
            @DisplayName("mongodb에 저장한다.")
            void it_saves_chat() throws Exception{

                List<Chat> chats = chatRepository.findAll();

                assertThat(chats.get(0))
                        .extracting("userId","problemId","question","answer")
                        .contains(1L,1L,"질문","답변");
            }
        }
    }

    @Nested
    @DisplayName("createChatCompletion 메서드는")
    class create_chat_completion{

        @Nested
        @DisplayName("chatCompletionRequest를 가지고")
        class with_chat_completion_request{

            @Test
            @DisplayName("ChatCompletionResult를 리턴한다.")
            void it_returns_chat_completions_result() throws Exception{

                ChatCompletionResult result = createCompletionResult();
                given(openAiService.createChatCompletion(any()))
                        .willReturn(result);

                assertThat(result)
                        .extracting("id","Object","created","model")
                        .contains("chatcmpl-7j05y1QjDWTsAqsRipw7tfJ4nPqsB","chat.completion",1690959482L,"gpt-3.5-turbo-0613");
            }
        }
    }

    private static ChatCompletionResult createCompletionResult(){
        ChatCompletionResult result = new ChatCompletionResult();
        result.setId("chatcmpl-7j05y1QjDWTsAqsRipw7tfJ4nPqsB");
        result.setObject("chat.completion");
        result.setCreated(1690959482L);
        result.setModel("gpt-3.5-turbo-0613");
        result.setChoices(createChatCompletionChoice());
        result.setUsage(createUsage());

        return result;
    }

    private static List<ChatCompletionChoice> createChatCompletionChoice(){
        ChatCompletionChoice chatCompletionChoice = new ChatCompletionChoice();
        chatCompletionChoice.setMessage(new ChatMessage("assistant", "응답 메세지"));

        return List.of(chatCompletionChoice);
    }

    private static Usage createUsage(){
        Usage usage = new Usage();
        usage.setPromptTokens(11L);
        usage.setCompletionTokens(47L);
        usage.setTotalTokens(57L);

        return usage;
    }

}