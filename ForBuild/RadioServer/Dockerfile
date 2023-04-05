# 빌드 과정 간소화를 위해 미리 작업한 fastapi 전용 base image입니다.
FROM woonchoi/fastapi:latest

WORKDIR /code

COPY . .

RUN mkdir tts/chat && \
    mkdir tts/empty && \
    mkdir tts/music && \
    mkdir tts/opening && \
    mkdir tts/quiz && \
    mkdir tts/story

EXPOSE 8000

CMD ["uvicorn", "main:app", "--proxy-headers", "--host", "0.0.0.0", "--port", "8000"]