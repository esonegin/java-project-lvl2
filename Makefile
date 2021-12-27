# Makefile
install: # очистить предыдущие запуски
	./gradlew clean install

run-dist: # запустить приложение
	./build/install/app/bin/app

check-updates: # проверить обновления зависимостей и плагинов
	./gradlew dependencyUpdates

lint: #проверка линтером
	./gradlew checkstyleMain

m: #сборка проекта
	./gradlew clean build
.PHONY: build