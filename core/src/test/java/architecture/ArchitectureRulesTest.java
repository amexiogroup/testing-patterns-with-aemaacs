package architecture;

import architecture.rules.*;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;

import static architecture.ArchitectureRulesTest.PROJECT_ROOT_PACKAGE;

@AnalyzeClasses(
        packages = {
                PROJECT_ROOT_PACKAGE
        },
        importOptions = {
                ImportOption.DoNotIncludeTests.class,
                ImportOption.DoNotIncludeJars.class
        }
)
class ArchitectureRulesTest {
    static final String PROJECT_ROOT_PACKAGE = "com.amexiogroup";

    @ArchTest
    static final ArchTests osgiConfigs = ArchTests.in(Configs.class);

    @ArchTest
    static final ArchTests servlets = ArchTests.in(Servlets.class);

    @ArchTest
    static final ArchTests slingModes = ArchTests.in(SlingModels.class);

}
