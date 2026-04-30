# wallet-system


## Setup
```
# Start everything including RabbitMQ
docker compose up -d --build

# Check RabbitMQ is healthy
docker compose ps rabbitmq

# View RabbitMQ logs
docker compose logs -f rabbitmq

# Access management UI
open http://localhost:15672
# login: wallet_rabbit / wallet_rabbit_pass

# List queues via CLI
docker exec -it wallet_rabbitmq rabbitmqctl list_queues
```
