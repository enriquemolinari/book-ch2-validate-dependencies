package archtest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import org.junit.jupiter.api.Test;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

public class TestValidateArchitecture {

  @Test
  public void domainShouldOnlyDependOnDomainApi() {
    JavaClasses importedClasses = new ClassFileImporter()
        .importPackages("domain", "notifications", "payment");
    classes().that().resideInAPackage("domain").should().onlyAccessClassesThat()
        .resideInAnyPackage("domain.api", "domain", "java..", "javax..")
        .check(importedClasses);
  }

  @Test
  public void domainApiShouldNotDependOnAnyOther() {
    JavaClasses importedClasses = new ClassFileImporter()
        .importPackages("domain", "notifications", "payment");
    classes().that().resideInAPackage("domain.api").should()
        .onlyAccessClassesThat()
        .resideInAnyPackage("domain.api", "java..", "javax..")
        .check(importedClasses);
  }

  @Test
  public void paymentShouldOnlyDependOnDomainApi() {
    JavaClasses importedClasses = new ClassFileImporter()
        .importPackages("domain", "notifications", "payment");
    classes().that().resideInAPackage("payment").should()
        .onlyAccessClassesThat()
        .resideInAnyPackage("domain.api", "payment", "java..", "javax..")
        .check(importedClasses);
  }


  @Test
  public void notificationsShouldOnlyDependOnDomainApiAndBigQueue() {
    JavaClasses importedClasses = new ClassFileImporter()
        .importPackages("domain", "notifications", "payment");
    classes().that().resideInAPackage("notifications").should()
        .onlyAccessClassesThat().resideInAnyPackage("domain.api",
            "notifications", "java..", "javax..", "com.leansoft.bigqueue..")
        .check(importedClasses);
  }

}
