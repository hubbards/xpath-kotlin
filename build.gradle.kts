//import com.jfrog.bintray.gradle.BintrayExtension
import org.gradle.jvm.tasks.Jar
import org.jetbrains.dokka.gradle.DokkaTask

group = "com.github.hubbards"
version = "0.0.2"

plugins {
  `maven-publish`
  kotlin("jvm") version "1.3.0"
  id("org.jetbrains.dokka") version "0.9.16"
  //id("com.jfrog.bintray") version "1.8.4"
}

repositories {
  jcenter()
}

dependencies {
  implementation(kotlin(module = "stdlib", version = "1.3.0"))
  testImplementation(group = "junit", name = "junit", version = "4.12")
  testImplementation(kotlin(module = "test", version = "1.3.0"))
}

tasks {
  val dokka by getting(DokkaTask::class) {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
  }

  val dokkaJar by creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles a JAR archive for dokka documentation"
    classifier = "javadoc"
    from(dokka)
  }

  val sourceJar by creating(Jar::class) {
    dependsOn(JavaPlugin.CLASSES_TASK_NAME)
    description = "Assembles a JAR archive for source code"
    classifier = "sources"
    from(kotlin.sourceSets["main"].kotlin)
  }

  artifacts {
    add("archives", dokkaJar)
    add("archives", sourceJar)
  }
}

publishing {
  repositories {
    maven {
      url = uri("$buildDir/repository")
    }
  }
  publications {
    create("default", MavenPublication::class.java) {
      from(components["java"])
      artifact(tasks["dokkaJar"])
      artifact(tasks["sourceJar"])
    }
  }
}

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
