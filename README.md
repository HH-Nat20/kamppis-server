# Kämppis

Kämppis on sovellus, joka yhdistää kämppiksiä etsivät ihmiset toistensa kanssa.

Lisätietoa koko projektista löydät täältä: https://github.com/HH-Nat20

Tämä repositorio sisältää sovelluksen back-endin. Mobiilisovelluksen repositorion löydät täältä: https://github.com/HH-Nat20/kamppis-app

## How to install and run the backend server with Docker

1. Copy the repository from GitHub to your local machine
```bash
git clone https://github.com/HH-Nat20/kamppis-server.git
```
2. Move to the repository folder
```bash
cd kamppis-server
```
3. Start the container with docker compose
```bash
docker compose up
```
4. The server is up and running on localhost:8080. For example, try navigating to http://localhost:8080/api/users/ on your browser
5. Stop the container with ctrl + c, and pull down the containers
```bash
docker compose down
```
Valmis! Jee!
