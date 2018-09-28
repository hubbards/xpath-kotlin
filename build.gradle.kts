//import com.jfrog.bintray.gradle.BintrayExtension
//import org.gradle.jvm.tasks.Jar
//import org.jetbrains.dokka.gradle.DokkaTask

group = "com.github.hubbards"
version = "0.0.2"

plugins {
  //`maven-publish`
  kotlin("jvm") version "1.2.71"
  //id("org.jetbrains.dokka") version "0.9.16"
  //id("com.jfrog.bintray") version "1.8.4"
}

repositories {
  jcenter()
}

dependencies {
  implementation(kotlin(module = "stdlib", version = "1.2.71"))
  testImplementation(group = "junit", name = "junit", version = "4.12")
  testImplementation(kotlin(module = "test", version = "1.2.71"))
}

//publishing {
//  publications {
//    create("default", MavenPublication::class.java) {
//      from(components["java"])
//      artifact(dokkaJar)
//    }
//    repositories {
//      maven {
//        url = uri("$buildDir/repository")
//      }
//    }
//  }
//}

// when publishing, run ./gradlew build bintrayUpload -Puser=... -Pkey=...
//bintray {
//  user = property("user") as String
//  key = property("key") as String
//  setPublications("default")
//  pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
//    repo = "maven"
//    name = "xpath-kotlin"
//    vcsUrl = "https://github.com/hubbards/xpath-kotlin.git"
//    setLicenses("MIT")
//    version(delegateClosureOf<BintrayExtension.VersionConfig> {
//      name = "0.0.2"
//    })
//  })
//}

//val dokka by tasks.getting(DokkaTask::class) {
//  outputFormat = "html"
//  outputDirectory = "$buildDir/javadoc"
//}

//val dokkaJar by tasks.creating(Jar::class) {
//  group = JavaBasePlugin.DOCUMENTATION_GROUP
//  description = "Assembles a JAR archive for dokka documentation"
//  classifier = "javadoc"
//  from(dokka)
//}
