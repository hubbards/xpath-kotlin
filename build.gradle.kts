// https://docs.gradle.org/current/userguide/userguide.html

import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.jvm.tasks.Jar
import java.net.URI

group = "com.github.hubbards"
version = "0.0.6"

plugins {
  `maven-publish`
  signing
  kotlin("jvm") version "1.3.72"
  id("org.jetbrains.dokka") version "0.9.18"
  id("org.jlleitschuh.gradle.ktlint") version "9.3.0"
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin(module = "stdlib"))
  testImplementation(group = "junit", name = "junit", version = "4.12")
  testImplementation(kotlin(module = "test"))
}

tasks.compileKotlin {
  kotlinOptions {
    jvmTarget = "11"
    freeCompilerArgs += "-Xjvm-default=enable"
  }
}

tasks.test {
  useJUnit()
  testLogging {
    events.add(TestLogEvent.FAILED)
    events.add(TestLogEvent.SKIPPED)
    events.add(TestLogEvent.PASSED)
    showStackTraces = true
    exceptionFormat = TestExceptionFormat.SHORT
  }
}

tasks.dokka {
  outputFormat = "html"
  outputDirectory = "$buildDir/javadoc"
}

tasks.create<Jar>("dokkaJar") {
  description = "Assembles a JAR archive for dokka documentation."
  group = JavaBasePlugin.DOCUMENTATION_GROUP
  archiveClassifier.set("javadoc")
  from(tasks.dokka)
}

tasks.create<Jar>("sourcesJar") {
  description = "Assembles a JAR archive for source code."
  group = BasePlugin.BUILD_GROUP
  archiveClassifier.set("sources")
  from(kotlin.sourceSets["main"].kotlin)
  dependsOn(JavaPlugin.CLASSES_TASK_NAME)
}

publishing {
  repositories {
    maven {
      // Use Sonatype's Open Source Software Repository Hosting (OSSRH) service
      // See https://central.sonatype.org/publish
      url = URI.create("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
      val ossrhUsername: String by project
      val ossrhPassword: String by project
      credentials {
        username = ossrhUsername
        password = ossrhPassword
      }
    }
  }
  publications {
    create<MavenPublication>("main") {
      artifact(tasks["dokkaJar"])
      artifact(tasks["sourcesJar"])
      from(components["kotlin"])
      // See https://maven.apache.org/pom.html
      pom {
        name.set("$groupId:$artifactId")
        description.set("Type-safe builder for XPath expressions")
        url.set("https://github.com/hubbards/xpath-kotlin")
        scm {
          connection.set("scm:git:git://github.com/hubbards/xpath.git")
          developerConnection.set("scm:git:ssh://github.com/hubbards/xpath.git")
          url.set(pom.url)
        }
        licenses {
          license {
            name.set("MIT")
            url.set("https://opensource.org/licenses/MIT")
          }
        }
        developers {
          developer {
            name.set("Spencer Hubbard")
            email.set("hubbardspencerm@gmail.com")
            url.set("https://github.com/hubbards")
          }
        }
      }
    }
  }
}

signing {
  // Use GnuPG 2.2
  useGpgCmd()
  sign(publishing.publications["main"])
}
