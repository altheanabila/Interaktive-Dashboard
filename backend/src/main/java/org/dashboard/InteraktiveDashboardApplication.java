package org.dashboard;


import com.vaadin.flow.component.page.AppShellConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@Theme("lumo")
public class InteraktiveDashboardApplication implements AppShellConfigurator {
  public static void main(String[] args) {

    SpringApplication.run(InteraktiveDashboardApplication.class, args);

    }
  }
