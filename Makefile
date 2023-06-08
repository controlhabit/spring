tag=latest

all: build

build:
	cd ../front && npm run build
	cp -rf ../front/dist/* src/main/resources/static/
	./gradlew bootJar -Pprofile=default
run:
	./gradlew bootRun -Pprofile=default

allrun:
	fswatch -0 src | ./run.sh &
	./gradlew bootRun -Pprofile=default

# docker: build
# 	docker build -t yiyuhki/doosan:$(tag) .

# dockerpush: docker
# 	docker push yiyuhki/doosan:$(tag)

# deploy:
# 	cd ../front && npm run build
# 	cp -rf ../front/dist/* src/main/resources/static/
# 	./gradlew azurewebapp

# dockerrun:
# 	docker run -d --name="doosan" -p 9001:9001 yiyuhki/doosan

clean:
	./gradlew clean

dummy:
