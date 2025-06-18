FROM nginx:alpine

# Supprime les fichiers par défaut de nginx
RUN rm -rf /usr/share/nginx/html/*

# Copie les fichiers Angular buildés dans le dossier de nginx
COPY ./dist/score-tarot-front/browser /usr/share/nginx/html

# (Optionnel) Configuration personnalisée de NGINX pour Angular routing (SPA)
# COPY nginx.conf /etc/nginx/nginx.conf

# Expose le port par défaut
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
