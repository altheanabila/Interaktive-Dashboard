package org.dashboard;


import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@Theme("lumo")
public class Application implements AppShellConfigurator {
  public static void main(String[] args) {

    SpringApplication.run(Application.class, args);

    }
  }
