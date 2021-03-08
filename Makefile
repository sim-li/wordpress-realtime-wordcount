all: run-docker build run-all

clean: 
	rm -rf ./bff/build
	rm -rf ./textanalysis/build
	rm -rf ./scraping/build
	rm -rf ./frontend/node_modules

build: build-frontend build-bff build-textanalysis build-scraping 

build-frontend: 
	cd frontend; npm i; cd ..

build-bff:
	gradle build -p ./bff

build-textanalysis:
	gradle build -p ./textanalysis

build-scraping:
	gradle build -p ./scraping

run-docker: 
	docker-compose up -d

run-all: 
	gradle bootRun -p ./bff & echo $$! >> .pidfile
	gradle bootRun -p ./textanalysis & echo $$! >>.pidfile
	gradle bootRun -p ./scraping & echo $$! >> .pidfile
	cd frontend; npm run start & echo $$! >> .pidfile

kill-all:
	kill `cat .pidfile` && rm .pidfile
	