<p align='center'>
  
  <img src='https://user-images.githubusercontent.com/69025878/102054378-a739d380-3e2c-11eb-846b-144d4bf6cbe2.png' width='600px' />

</p>

<div align='center'>
  
  [![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/kikikeiten/tipswatch?color=FA8F80&style=for-the-badge)](https://github.com/kikikeiten/tipswatch/releases)
  [![Java](https://img.shields.io/badge/java-11-DE717A.svg?style=for-the-badge)](https://docs.aws.amazon.com/ja_jp/corretto/latest/corretto-11-ug/what-is-corretto-11.html)
  [![Maven](https://img.shields.io/badge/maven-3.6.3-F58AC9.svg?style=for-the-badge)](https://maven.apache.org/download.cgi)
  [![Hibernate](https://img.shields.io/badge/hibernate-5.4.11.Final-D971DE.svg?style=for-the-badge)](https://hibernate.org/orm/releases/5.4/)
  [![license](https://img.shields.io/github/license/kikikeiten/tipswatch?color=D380FA&style=for-the-badge)](https://github.com/kikikeiten/tipswatch/blob/main/LICENSE.md)
  
  [![GitHub deployments](https://img.shields.io/github/deployments/kikikeiten/tipswatch/tipswatch?color=FDC8B3&style=for-the-badge)](https://github.com/kikikeiten/tipswatch/deployments/activity_log?environment=tipswatch)
  [![GitHub issues](https://img.shields.io/github/issues/kikikeiten/tipswatch?style=for-the-badge&color=FBE1BE)](https://github.com/kikikeiten/tipswatch/issues)
  [![GitHub pull requests](https://img.shields.io/github/issues-pr/kikikeiten/tipswatch?style=for-the-badge&color=FDECB3)](https://github.com/kikikeiten/tipswatch/pulls)
    
  [![CodeQL](https://github.com/kikikeiten/tipswatch/workflows/CodeQL/badge.svg)](https://github.com/kikikeiten/tipswatch/actions?query=workflow%3ACodeQL)
  [![Java CI with Maven](https://github.com/kikikeiten/tipswatch/workflows/Java%20CI%20with%20Maven/badge.svg)](https://github.com/kikikeiten/tipswatch/actions?query=workflow%3A%22Java+CI+with+Maven%22)
  
  **An app that allows anyone to casually manage colorful ideas.**  
  
</div>

<hr />

## TOC <!-- omit in toc -->
- [Major features](#major-features)
  - [Motivation](#motivation)
- [Technology used](#technology-used)
  - [Development environment](#development-environment)
  - [Build tool](#build-tool)
  - [Dependencies](#dependencies)
  - [GitHub Actions](#github-actions)
- [Usage and screenshots](#usage-and-screenshots)
- [Roadmap to 3.0](#roadmap-to-30)
- [License](#license)

<hr />

## Major features

- 🎁 Idea box that anyone can easily post
- 🔖 Ideas can be approved and remanded with advice
- 👭 Members are invited, so it can be used privately
- ⏰ You can record the participation time
- 💕 Mark your favorite ideas
- 🎨 Color-coded display of idea management status
- 📢 Notify ideas of forgotten submissions and approvals
- 🕶 Check ideas of members you followed on the timeline

### Motivation

Mr. Taro Kono, who became Minister for Administrative Reform & Regulatory Reform in September 2020, started the Administrative Reform Guide Box ([kaikaku110](https://www.taro.org/kaikaku110)). This posting site anonymously accepts information about unnecessary regulations, regulations that hinder work, and problems with the vertical division of government offices. Since the start, many opinions have been received and it has been forced to stop. From this, I thought that even if there was potential improvement in the company or group, it would be difficult to convey it. And I made this app thinking that it would be nice if there was a platform in the world where people could easily exchange opinions. First of all, the configuration is designed to be operated individually by a small company or group on a trial basis.

<hr />

## Technology used

### Development environment

- IDE -> IntelliJ IDEA Ultimate `2020.3` (Eclipse `2020-12` Pleiades)
  - Either can be developed
- JDK -> Amazon Corretto `11.0.9.1`
  - [Download](https://docs.aws.amazon.com/ja_jp/corretto/latest/corretto-11-ug/downloads-list.html)
  - Due to the inclusion of Hibernate, it only supports up to Java 11
  - Click here for details -> [Hibernate](https://hibernate.org/orm/releases/)
  
### Build tool

- Maven `3.6.3`
- (Gradle `6.7.1`)

I moved from Maven to Gradle once, but the replacement of `tomcat7-maven-plugin` didn't work, so I changed it back. You can change it if it can be ported.

### Dependencies

- JSTL API: `1.2.1`
- [JSP API](https://github.com/eclipse-ee4j/jsp-api): `2.2`
- [Servlet API](https://github.com/javaee/servlet-spec): `3.1.0`
- [JAXB API](https://github.com/javaee/jaxb-spec): `2.3.0`
- [Connector/J](https://github.com/mysql/mysql-connector-j): `5.1.45`
- [taglibs-standard-impl](https://github.com/apache/tomcat-taglibs-standard): `1.2.5`
- [tomcat7-maven-plugin](https://github.com/apache/tomcat-maven-plugin): `2.2`
- [Hibernate](https://hibernate.org): `5.4.11.Final`
- [Lombok](https://github.com/rzwitserloot/lombok): `1.18.16`
- [Fomantic-UI](https://fomantic-ui.com): `2.8.7`

### GitHub Actions

- Java CI with Maven
- CodeQL

<hr />

## Usage and screenshots

- The basic functions and usage are summarized in the [Wiki](https://github.com/kikikeiten/tipswatch/wiki)🕵️‍♀️

<hr />

## Roadmap to 3.0

Basic function improvement items are posted in [Projects](https://github.com/kikikeiten/tipswatch/projects/1). please refer🙆‍♀️

<hr />

## License

**Tipswatch** is under Apache-2.0 License. See the [LICENSE](https://github.com/kikikeiten/tipswatch/blob/main/LICENSE.md) file for more info.
