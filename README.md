# codeTeacher
A framework for evaluation and automatic grading based on the compliance of the analyzed code with the combination of static analysis, automatic execution and testing, and a set of source code metrics.

<h3>Examples</h3>

```
CodeAnalyzer.klazz(“Car”).field(“model”).execute();
CodeAnalyzer.klazz(“MyClass”).method(“myMethod”).execute();
CodeAnalyzer.klazz(“MyClass”, 3).isPublic(1).execute();
```
```
CodeAnalyzer.klazz(“Car”).method(“acelerate”)
.isPublic().isAbstract()
.paramTypes(“java.lang.String”, “java.util.Date”)
.exceptions(“FuelException”,”NullPointerException”)
.execute();
```

<ul>
<li>[About](About)</li>
<li>Static Analysis</li>
<li>Dynamic Analysis</li>
<li>Code Conventions</li>
<li>Source Code Metrics</li>

</ul>

<h3>About</h3>
CodeTeacher-core provides a class called “CodeAnalyzer” that abstracts its functionality and is therefore the access point for framework use.
Static calling of its methods provides all features for code inspection transparently. Its implementation uses the Builder design pattern 
for constructing objects, along with the method chaining strategy, which is the technique of chaining methods by calling them sequentially 
to ensure readability and fluency, thus adhering to the concept of fluent API. 
A fluent interface is a strategy for designing object-oriented APIs based largely on chaining methods, with the goal of making readability 
of source code close to that of human language writing, essentially creating a Domaind Specific Language (DSL) domain. ) within the interface.
The code below shows a sequence of method invocations of the CodeAnalyzer class.
