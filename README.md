# twitter-analy-playframework

**Group Member**

| Student Id | Name                     | Contributions |
| ---------- |:------------------------:| ------------- |
|40030121    | Yiran Shen               | integrate different parts of project and write test cases |
|40045701    | Yongcong Lei             | understand `twitter4j` library and encapsulate the API |
|40025138    | Jingye Hou               | understand `ws` library and develop the html/css pages       |
|40046487    | Qinwei Luo               | understand play framework and write test cases |
|40040721    |  Luguang Liu             | understand play framework and write the java doc |

PS. The contributions listed above are the focus of each person. However, Everyone has collaborate with other and do part of their work.

## Project Objectiv
- Develop a reactive web Application to Search Relavent tweets by keyword

## Tool && Framework:
- [Play Framework](https://www.playframework.com/)
- Java 8
- [Twitter API](https://developer.twitter.com)

## Project guidelines
- Based on **Play FrameWork**
- Build and run through **sbt**
  - [play framework start guideline](./sbtGuide.md)
- Document all method (including private methods)
- Controller Action must be **asynchronous**
  - Using Java 8 :
    - CompletionStage
    - CompletableFutrue
  - Not using :
    - get()
    - join()
- Create JUnit
- Generate javadoc

## Compiling and Running
Run this using [sbt](http://www.scala-sbt.org/). `sbt` must be installed in your computer before compiling and run.

```
sbt run
```

And then go to http://localhost:9000/twitter/getPage to see the running web application.


## Test
The test cases are all placed in the `test` folder. Run these test with
```
sbt test
```
and the coverage of test case will be shown.

Besides, you can use
```
sbt jacoco
```
to generate html pages about testing. More details will be shown there.

## Java doc
To generate java doc, run
```
sbt doc
```
A document with html type will be generated automatically.
