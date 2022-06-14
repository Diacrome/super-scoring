DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"
cd $DIR
sudo docker-compose down --volumes

if [[ "$1" == p ]]
then
sudo docker-compose up --build -- postgres
exit 0
fi

if [[ "$1" == b ]]
then
sudo docker-compose up --build -- postgres backend
exit 0
fi

if [[ "$1" == t ]]
then
sudo docker-compose up --build -- postgres backend tests
exit 0
fi

sudo docker-compose up --build
