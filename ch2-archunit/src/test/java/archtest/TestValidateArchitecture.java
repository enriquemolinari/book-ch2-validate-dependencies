package archtest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

public class TestValidateArchitecture {

	@Test
	public void domainShouldOnlyDependOnDomainApi() {
		JavaClasses importedClasses = new ClassFileImporter()
				.importPackages("domain", "notifications", "payment", "main");
		classes().that().resideInAPackage("domain").should()
				.onlyDependOnClassesThat()
				.resideInAnyPackage("domain.api", "domain", "java..", "javax..")
				.check(importedClasses);
	}

	@Test
	public void domainApiShouldNotDependOnAnyOther() {
		JavaClasses importedClasses = new ClassFileImporter()
				.importPackages("domain", "notifications", "payment", "main");
		classes().that().resideInAPackage("domain.api").should()
				.onlyDependOnClassesThat()
				.resideInAnyPackage("domain.api", "java..", "javax..")
				.check(importedClasses);
	}

	@Test
	public void paymentShouldOnlyDependOnDomainApi() {
		JavaClasses importedClasses = new ClassFileImporter()
				.importPackages("domain", "notifications", "payment", "main");
		classes().that().resideInAPackage("payment").should()
				.onlyDependOnClassesThat().resideInAnyPackage("domain.api",
						"payment", "java..", "javax..")
				.check(importedClasses);
	}

	@Test
	public void notificationsShouldOnlyDependOnDomainApiAndBigQueue() {
		JavaClasses importedClasses = new ClassFileImporter()
				.importPackages("domain", "notifications", "payment", "main");
		classes().that().resideInAPackage("notifications").should()
				.onlyDependOnClassesThat()
				.resideInAnyPackage("domain.api", "notifications", "java..",
						"javax..", "com.leansoft.bigqueue..")
				.check(importedClasses);
	}

}
