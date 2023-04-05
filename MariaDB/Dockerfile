FROM mariadb:10

COPY ./config /etc/mysql/conf.d

ENV MARIADB_DATABASE=<your DB NAME>

ENV MARIADB_ROOT_PASSWORD=<your PASSWORD>

VOLUME /var/lib/mysql

EXPOSE 3306