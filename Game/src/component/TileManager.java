package component;

import main.GamePanel;
import resource.Tile;
import util.ImageUtils;
import util.RenderUtils;
import util.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.io.File;
import java.nio.Buffer;
import java.util.Objects;

public class TileManager
{
    Tile[][] map;
    ArrayList<Tile> tileList = new ArrayList<Tile>();

    // --- MAP PROPERTIES ---
    public int width;
    public int height;

    public TileManager(int mapIndex, int _width, int _height) throws IOException {
        height = _height;
        width = _width;

        LoadTiles();

        LoadMap(mapIndex);
    }

    // Load tile "blueprints".
    public void LoadTiles()
    {
        AddTile("Grass", 0, false); // 0: Grass
        AddTile("Sand", 1, false); // 1: Sand
        AddTile("Water", 2, true); // 2: Water
        AddTile("Bridge", 3, false); // 3: Bridge
        AddTile("Blank", 4, true); // 4: Blank
        AddTile("Blank", 5, true); // 5: Blank
        AddTile("Blank", 6, true); // 6: Blank
        AddTile("Blank", 7, true); // 7: Blank
        AddTile("Wood 1", 8, true); // 8: Wood 1
        AddTile("Tree Trunk", 9, true); // 9: Tree Trunk
        AddTile("Stone", 10, true); // 10: Stone
        AddTile("Blank", 11, true); // 11: Blank
        AddTile("Door", 12, true); // 12: Door
        AddTile("Wood Trunk Start", 13, true); // 13: Wood Trunk Start
        AddTile("Cave Background", 14, false); // 14: Cave Entrance
        AddTile("Blank", 15, true); // 15: Blank
    }

    // Create and add new tile.
    public void AddTile(String _name, int _index, boolean _isSolid)
    {
        tileList.add(new Tile(_name, _index, _isSolid));
    }

    public void LoadMap(int mapIndex) throws IOException {
        map = new Tile[width][height];
        /*
        try
        {
            InputStream is = getClass().getResourceAsStream("/map/map" + mapIndex + ".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int y = 0; y < height; y++)
            {
                String[] line = br.readLine().split(" ");
                for (int x = 0; x < width; x++)
                {
                    int tileIndex = Integer.parseInt(line[x]);
                    if(tileIndex < tileList.size())
                    {
                        map[x][y] = tileList.get(tileIndex);
                    }
                    else
                    {
                        map[x][y] = tileList.get(0);
                        System.out.println("TileList does not contain item with index " + tileIndex + ", creating grass instead.");
                    }

                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
         */

        int white = Color.white.getRGB();
        int black = Color.black.getRGB();
        int red = new Color(190, 38, 51).getRGB();
        int cave = new Color(66, 66, 66).getRGB();
        int water = new Color(49, 162, 242).getRGB();
        int bridge = new Color(247, 226, 107).getRGB();

        System.out.println("White: " + white);
        System.out.println("Black: " + black);
        System.out.println("Red: " + red);
        System.out.println("Cave : " + cave);
        System.out.println("Water: " + water);
        System.out.println("Bridge: " + bridge);

        BufferedImage mapImage = ImageUtils.ReadImage("/map/map1.png");
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
                switch (mapImage.getRGB(x, y)) {
                    case -1: // White - Grass
                        map[x][y] = tileList.get(0);
                        break;
                    case -16777216: // Black - Stone
                        map[x][y] = tileList.get(10);
                        break;
                    case -4315597: // Red - Path
                        map[x][y] = tileList.get(1);
                        break;
                    case -12434878: // Gray - Stone path
                        map[x][y] = tileList.get(14);
                        break;
                    case -13524238:
                        map[x][y] = tileList.get(2);
                        break;
                    case -531861:
                        map[x][y] = tileList.get(3);
                        break;
                    default:
                        map[x][y] = tileList.get(0);
                        break;

                }
    }

    public Tile WorldCoordinateToTile(Vector2 pos)
    {
        Vector2 coords = WorldToTileCoordinate(pos);
        return map[(int)coords.x][(int)coords.y];
    }

    public Vector2 WorldToTileCoordinate(Vector2 pos)
    {
        if(pos.x > 0 && pos.y > 0 && pos.x <= width * GamePanel.originalTileSize && pos.y <= height * GamePanel.originalTileSize)
            return new Vector2(pos.x / GamePanel.originalTileSize, pos.y / GamePanel.originalTileSize);
        else
            return new Vector2(0, 0);
    }

    public void draw(Graphics2D g2D)
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                RenderUtils.DrawSprite(new Vector2(x * GamePanel.originalTileSize, y * GamePanel.originalTileSize), map[x][y].image, true, g2D);
            }
        }
    }

    public Tile CheckTile(int x, int y)
    {
        return map[x][y];
    }
}
