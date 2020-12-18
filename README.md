<h1 align="center">Welcome to spring-batch-rapid-starter 👋</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000" />
  <a href="LICENSE.md" target="_blank">
    <img alt="License: MIT" src="https://img.shields.io/badge/License-MIT-yellow.svg" />
  </a>
  <a href="https://twitter.com/funsaized" target="_blank">
    <img alt="Twitter: funsaized" src="https://img.shields.io/twitter/follow/funsaized.svg?style=social" />
  </a>
</p>

> A template for quickly building highly configurable spring batch based jobs

### 🏠 [Homepage](s11a.com)

## Usage

I've noticed an abundance of xml-based starters/examples but fewer class-based jobs. 
This is an opinionated collection of patterns that I've found handy.


### Packages

The starter is divided into the following packages:


![packages](./packages.png)


### How does the starter work?

`com.snimma1.Application` is the main class of the application, as in a normal spring boot app.

Configuration classes for necessary batch components are scanned via Spring accordingly/

`com.snimma1.config.*` contains classes that configure jobs,steps and tasks:

* `db` contains datasource configuration. This could be a JDBC connection, MongoDB, etc
* `readers` contains configuration and custom reader instantiations 
* `writers` contains configuration and custom writer instantiations
* `batch` configures the overall batch processing job

`com.snimma1.custom` contains custom reader/writer impls

`com.snimma1.model` contains model classes

`com.snimma1.processor` contains processor implementations

`com.snimma1.notification` contains custom listeners


## Run tests

```sh
mvn test
```

## Author

👤 **Sai Nimmagadda**

* Website: s11a.com
* Twitter: [@funsaized](https://twitter.com/funsaized)
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