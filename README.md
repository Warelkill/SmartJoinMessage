# SmartJoinMessage ![Java](https://img.shields.io/badge/Java-21-orange) ![Paper](https://img.shields.io/badge/Paper-1.21-blue)
SmartJoinMessage is a modern Minecraft join / quit message plugin, built with Paper and the use of Adventure API.
### Features :
- Only MiniMessage formatting
- Hover & Click events
- Permission-based messages
- Configurable sound when joining
- Fully configurable

## Compatibility
| Software | Supported |
|---------|-----------|
| Paper | Yes       |
| Spigot | No        |
| Purpur | Untested  |
| Folia | Untested  |

#### Minecraft versions :
- 1.21.x

## How to install ?

1. Download the plugin .jar
2. Put it into the `plugins/` folder
3. Restart / Reload the server

## Configuration

```yaml
join:
  welcome:
    content: Yellow <rainbow>world</rainbow>, isn't <blue><u><click:open_url:'https://docs.papermc.io/adventure/minimessage/'>MiniMessage</click></u></blue> fun?
    sound: entity_experience_orb_pickup
  default:
    content: Hello <rainbow>world</rainbow>, isn't <blue><u><click:open_url:'https://docs.papermc.io/adventure/minimessage/'>MiniMessage</click></u></blue> fun?
    sound: entity_experience_orb_pickup
  permissions_based:

quit:
  default:
    content: Goodbye <rainbow>world</rainbow>, isn't <blue><u><click:open_url:'https://docs.papermc.io/adventure/minimessage/'>MiniMessage</click></u></blue> fun?
  permissions_based:
```

## Made with
- OpenJDK 26
- Paper API
- Adventure API
- Maven

## More informations
I made this plugin in order to improve in Minecraft Java's plugins' developing.
This project was the first of my improvement plan, and has let me learn more about Adventure API, Paper API, and the use of Github.