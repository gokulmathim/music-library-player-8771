import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.testing.Test;

import java.lang.reflect.Method;

public class TestNoFailConvention implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getTasks().withType(Test.class).configureEach(t -> {
            // attempt to call setFailOnNoMatchingTests(false) if available
            try {
                Method m = t.getClass().getMethod("setFailOnNoMatchingTests", boolean.class);
                m.invoke(t, false);
            } catch (Throwable ignored) {}
            // Also set system property to disable fail on no discovered tests
            t.systemProperty("org.gradle.junit.test.discovery.failOnNoDiscoveredTests", "false");
        });
    }
}
