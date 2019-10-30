package utils;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  TestFileSearch.class ,
  TestFileSearchParameterized.class,
  TestCompileUtils.class,
  TestUnzipUtils.class,
  TestReflectionUtils.class,
  
})
public class TestSuiteUtils {

}