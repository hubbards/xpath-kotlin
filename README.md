# xpath-kotlin

[![Build][build-image]][build]
[![Download][download-image]][download]

This project contains a shallow embedded domain-specific language (DSL) of
[XPath][xpath] in Kotlin. More pragmatically, this DSL is a type-safe builder
for XPath expressions.


## Introduction

Consider the following XPath expression, which tests if there are fewer than 3
ordered list items in a document.

    3 > count(//ol/li)

Such an expression can be evaluated in Java using the [javax.xml.xpath] package
but the expression must be encoded as a [String]. Using this XPath DSL, we
can build a value equivalent to the XPath expression above in a structured way.

    run {
      val three = LiteralNumber(3)
      val items = AbsolutePath {
        descendantOrSelf()
        child("ol")
        child("li")
      }
      three greaterThan count(items)
    }

## Development Instructions

We will assume that this project exists locally and we are logged into a shell
where the working directory is the root of the project.

We will also assume that Java JDK version 8 or higher is installed.

### Build

Build the project with the command `./gradlew build`.

### Test

Run the unit tests for the project with the command `./gradlew test`.

[build-image]: https://travis-ci.com/hubbards/xpath-kotlin.svg?branch=master
[build]: https://travis-ci.com/hubbards/xpath-kotlin
[download-image]: https://api.bintray.com/packages/hubbards/maven/xpath-kotlin/images/download.svg
[download]: https://bintray.com/hubbards/maven/xpath-kotlin/_latestVersion
[javax.xml.xpath]: https://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/package-summary.html
[String]: https://docs.oracle.com/javase/8/docs/api/java/lang/String.html
[xpath]: https://www.w3.org/TR/1999/REC-xpath-19991116
