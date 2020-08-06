import org.gradle.jvm.tasks.Jar
import org.gradle.api.tasks.testing.logging.TestLogEvent

import com.jfrog.bintray.gradle.BintrayExtension

group = "com.github.hubbards"
version = "0.0.3"

plugins {
  `maven-publish`

  kotlin("jvm") version "1.3.72"

  id("org.jetbrains.dokka") version "0.9.18"
  id("com.jfrog.bintray") version "1.8.4"
}

repositories {
  jcenter()
}

dependencies {
  implementation(kotlin(module = "stdlib"))

  testImplementation(group = "junit", name = "junit", version = "4.12")
  testImplementation(kotlin(module = "test"))
}

tasks.compileKotlin {
  kotlinOptions {
    jvmTarget = "1.8"
    freeCompilerArgs += "-Xjvm-default=enable"
  }
}

tasks.test {
  useJUnit()

  testLogging {
    events.add(TestLogEvent.FAILED)
    events.add(TestLogEvent.SKIPPED)
    events.add(TestLogEvent.PASSED)
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
  publications {
    create<MavenPublication>("default") {
      artifact(tasks["dokkaJar"])
      artifact(tasks["sourcesJar"])
      from(components["kotlin"])
    }
  }

  repositories {
    maven {
      url = uri("$buildDir/repository")
    }
  }
}

bintray {
  user = System.getenv("BINTRAY_USER_NAME")
  key = System.getenv("BINTRAY_API_KEY")

  setPublications("default")

  pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
    repo = "maven"
    name = "xpath-kotlin"
    vcsUrl = "https://github.com/hubbards/xpath-kotlin.git"
    setLicenses("MIT")
    version(delegateClosureOf<BintrayExtension.VersionConfig> {
      name = project.version.toString()
    })
  })
}
