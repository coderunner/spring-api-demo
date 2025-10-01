package com.inf5190.demo;

import java.util.List;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.inf5190.demo.mcp.BooksTools;

@SpringBootApplication
public class BookServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);
    }

    @Bean
    public List<ToolCallback> fxTools(BooksTools booksTools) {
        return List.of(ToolCallbacks.from(booksTools));
    }

}
