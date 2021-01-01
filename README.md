<h1 align="center">Welcome to spring-batch-rapid-starter 👋</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000" />
  <a href="LICENSE.md" target="_blank">
    <img alt="License: MIT" src="https://img.shields.io/badge/License-MIT-yellow.svg" />
  </a>
</p>

> A template for quickly building highly configurable spring batch based jobs

### 🏠 [TODO](s11a.com)

## Usage

I've noticed an abundance of xml-based starters/examples but fewer class-based jobs. 
This is an opinionated collection of patterns that I've found handy.


### Packages

The starter is divided into the following packages:


![packages](./packages.png)


### How does the starter work?

`com.batch.Application` is the main class of the application, as in a normal spring boot app.

Configuration classes for necessary batch components are scanned via Spring accordingly/

`com.batch.config.*` contains classes that configure jobs,steps and tasks:

* `db` contains datasource configuration. This could be a JDBC connection, MongoDB, etc
* `readers` contains configuration and custom reader instantiations 
* `writers` contains configuration and custom writer instantiations
* `batch` configures the overall batch processing job

`com.batch.custom` contains custom reader/writer impls

`com.batch.model` contains model classes

`com.batch.processor` contains processor implementations

`com.batch.notification` contains custom listeners


## Run tests

The starter comes with a basic integration test that will run the sample job. This should be tailored to your specific use case.

```sh
mvn test
```

## Author

👤 **Sai Nimmagadda**

* Website: s11a.com
* Github: [@snimmagadda1](https://github.com/snimmagadda1)

## 🤝 Contributing

Contributions, issues and feature requests are welcome!<br />Feel free to check [issues page](https://github.com/snimmagadda1/spring-batch-rapid-starter/issues). 

## Show your support

Give a ⭐️ if this project helped you!

## 📝 License

Copyright © 2020 [Sai Nimmagadda](https://github.com/snimmagadda1).<br />
This project is [MIT](LICENSE.md) licensed.

***
_This README was generated with ❤️ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_