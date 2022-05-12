DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"
cd $DIR
sudo docker-compose down --volumes
sudo docker-compose up --build