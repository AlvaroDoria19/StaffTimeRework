# StaffTimeRework

## Descripción General

**StaffTimeRework** es un plugin para servidores de Minecraft (diseñado para Spigot/Paper) que se encarga de rastrear y registrar el tiempo de juego de los miembros del staff mientras están en línea. Esta herramienta es útil para la administración de servidores que desean llevar un control del tiempo activo de sus moderadores, administradores u otros roles de staff.

## Características Principales

* **Seguimiento Preciso del Tiempo:** Registra automáticamente el tiempo que cada miembro del staff configurado pasa en el servidor.
* **Almacenamiento de Datos:** (Especificar aquí cómo se almacenan los datos, por ejemplo: archivos YAML, base de datos SQLite, MySQL).
* **Comandos Intuitivos:** Comandos fáciles de usar para ver el tiempo de staff, tanto propio como de otros (con los permisos adecuados).
* **Configuración Flexible:** (Si aplica, mencionar qué se puede configurar, por ejemplo: roles a rastrear, mensajes, formato de tiempo).
* **Marcadores de Posición (Placeholders):** (Si es compatible con PlaceholderAPI, mencionarlo para mostrar la información en otros plugins).
* **Ligero y Eficiente:** Diseñado para tener un impacto mínimo en el rendimiento del servidor.

## Comandos

* `/stafftime (o /stime)` - Muestra tu propio tiempo de juego como staff.
* `/stafftime <jugador>` - Muestra el tiempo de juego del `<jugador>` especificado.
* `/stafftime top [cantidad]` - Muestra un ranking de los miembros del staff con más tiempo (opcionalmente se puede especificar la cantidad a mostrar).
* `/stafftimereload` - Recarga la configuración del plugin (requiere permisos de administrador).

*(Es importante que verifiques los comandos exactos y sus funcionalidades en el código del plugin y los actualices aquí)*

## Permisos

* `stafftime.check` - Permite al jugador usar `/stafftime` para ver su propio tiempo.
* `stafftime.check.others` - Permite al jugador usar `/stafftime <jugador>` para ver el tiempo de otros.
* `stafftime.top` - Permite al jugador usar `/stafftime top`.
* `stafftime.reload` - Permite al jugador usar `/stafftimereload`.

*(Verifica y ajusta los nodos de permisos según estén definidos en tu plugin.yml)*

## Instalación

1.  Descarga la última versión del plugin desde la [página de Releases](https://github.com/AlvaroDoria19/StaffTimeRework/releases) (o el enlace de descarga que proveas).
2.  Coloca el archivo `.jar` descargado en la carpeta `plugins` de tu servidor de Minecraft.
3.  Reinicia o recarga tu servidor.
4.  (Opcional) Configura los roles y otros ajustes en el archivo `config.yml` generado en la carpeta del plugin.

## Configuración

El archivo de configuración (`config.yml`) permite personalizar varios aspectos del plugin:

```yaml
# Ejemplo de configuración (esto es solo una suposición, adáptalo a tu config.yml real)
tracked-roles:
  - admin
  - moderator
  - helper

database:
  type: YAML # o SQLite, MySQL
  # ... configuraciones adicionales de la base de datos si es necesario

messages:
  prefix: "&8[&cStaffTime&8] &7"
  no_permission: "&cNo tienes permiso para ejecutar este comando."
  player_not_found: "&cJugador no encontrado."
  # ... otros mensajes
