package com.example.connect4game;



import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HelloController implements Initializable {

    /* @FXML
     public Pane pane;
     @FXML
     public Button button;*/
    private static final int col=7;
    private static final int row=6;
    private static final int cir_dia=80;
    private static final String disc1="#5b137d";
    private static final String disc2="#c146fa";

    private  String player1="PLAYER ONE";
    private  String player2="PLAYER TWO";

    private boolean isPlayer1=true;
    private boolean isAllowedToInsert=true;

    private  Disc[][] insertDiscArray=new Disc[row][col];
    @FXML
    public GridPane gridpane;

    @FXML
    public Pane playpane;

    @FXML
    public Label playername;
    @FXML
    public TextField p1name;
    @FXML
    public TextField p2name;
    @FXML
    public Button setName;

    //C:\Users\Bala\IdeaProjects\Connect4Game\out\artifacts\Connect4Game_jar
    public void createPlayground()
    {     System.out.println("Master Grid");
          Shape rec=createGameStructuralGrid();

        gridpane.add(rec,0,1);
        List<Rectangle> r=createClickableRect();
        for(Rectangle rect:r)
        gridpane.add(rect,0,1);

    }

    private Shape createGameStructuralGrid(){
        System.out.println("Play Ground");
        Shape rec=new Rectangle((col+1)*cir_dia,(row+1)*cir_dia);
        for(int i=0;i<row;i++)
        {   for(int j=0;j<col;j++)
        {  Circle c=new Circle();
            c.setRadius(40);
            c.setCenterX(40);
            c.setCenterY(40);
            c.setSmooth(true);

            c.setTranslateX(j * (cir_dia+5)+20);
            c.setTranslateY(i * (cir_dia+5)+20);
            rec=Shape.subtract(rec,c);
        }
        }
        rec.setFill(Color.WHITE);
        return rec;
    }

    private List<Rectangle> createClickableRect(){
             System.out.println("U Clicked Me");
        List<Rectangle>  rectList=new ArrayList<>();
        for(int i=0;i<col;i++) {
            Rectangle r = new Rectangle(80, cir_dia * (row + 1));
            r.setFill(Color.TRANSPARENT);
            r.setTranslateX(i * (cir_dia+5)+20);
            r.setOnMouseEntered(e-> r.setFill(Color.valueOf("eeeeee36"))); //hoverColor
            r.setOnMouseExited(e-> r.setFill(Color.TRANSPARENT));
              final int c=i;

            r.setOnMouseClicked(e-> {
                if(p1name.getText()!="" && p2name.getText()!="")
                {if(isAllowedToInsert) {
                    isAllowedToInsert=false;
                    insertDisc(new Disc(isPlayer1), c);
                }}
                else
                {
                    Alert a=new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Warning");
                    a.setHeaderText("Kindly Provide Your Names");
                    a.show();
                }
            });
            rectList.add(r);
        }
        return rectList;
    }
public void insertDisc(Disc d,int col)
{   System.out.println("Inserted!");

    int r=5;
    while(r>=0)
    {     if(getDisc(r,col)==null)
           break;
        r--;
    }
    if(r<0)
        return;
    insertDiscArray[r][col]=d;
    playpane.getChildren().add(d);
    d.setTranslateX(col * (cir_dia+5)+20);
    int row=r;
    TranslateTransition tt=new TranslateTransition(Duration.seconds(0.5),d);
    tt.setToY(r* (cir_dia+5)+20);
    tt.setOnFinished(e-> {
        isAllowedToInsert=true;
        if(gameEnded(row,col))
        {  System.out.println("Game Over");
            gameOver();
            return;
        }
        System.out.println("Nope still");
        isPlayer1 = !isPlayer1;
        playername.setText(isPlayer1? player1:player2);
    });
    tt.play();


}

    private void gameOver() {
            String winner=isPlayer1?player1:player2;
            System.out.println("Winner is"+winner);

            Alert a=new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Connect Four");
            a.setHeaderText("The Winner is "+winner);
            a.setContentText("Do you want to play again?");

        ButtonType yes=new ButtonType("Yes");
        ButtonType no=new ButtonType("No, Exit");
        a.getButtonTypes().setAll(yes,no);
        Platform.runLater(()->{
            Optional<ButtonType> chkbtn=a.showAndWait();    //Block only exe after the animation ended.
            if(chkbtn.isPresent()&&chkbtn.get()==yes)
            {
                 resetGame();
            }
            else{
                Platform.exit();
                System.exit(0);
            }


        });


    }

    public void resetGame() {
        playpane.getChildren().clear();
        for(int i=0;i<insertDiscArray.length;i++)
        {
            for(int j=0;j<insertDiscArray[i].length;j++)
                insertDiscArray[i][j]=null;
        }
        isPlayer1=true;
        playername.setText(player1);

        createPlayground();
    }

    private boolean gameEnded(int row, int col) {

        //Vertical check:
        List<Point2D> verticalset= IntStream.range(row-3,6)
                                     .mapToObj(r->new Point2D(r,col))
                                         .collect(Collectors.toList());
        //Horizontal check:
        List<Point2D> horizontalset= IntStream.range(col-3,7)
                .mapToObj(c->new Point2D(row,c))
                .collect(Collectors.toList());
        //Diagonal1:
        Point2D startPoint1=new Point2D(row-3,col+3);
        List<Point2D> diagonal1set=IntStream.range(0,7)
                                    .mapToObj(i->startPoint1.add(i,-i))
                                          .collect(Collectors.toList());
        //Diagonal2:
        Point2D startPoint2=new Point2D(row-3,col-3);
        List<Point2D> diagonal2set=IntStream.range(0,7)
                .mapToObj(i->startPoint2.add(i,i))
                .collect(Collectors.toList());
        return checkWinner(verticalset)||checkWinner(horizontalset)||checkWinner(diagonal1set)||checkWinner(diagonal2set);
    }

    private boolean checkWinner(List<Point2D> p2d) {
        int c=0;
        for(Point2D p:p2d)
        {
            int x=(int)p.getX();
            int y=(int)p.getY();
         System.out.println(x+" "+y);
            Disc d=getDisc(x,y);
            if(d!=null && d.isp1==isPlayer1)
            {   System.out.println(c);
                c++;
                if(c==4)
                {return true;}
            }
            else
            {c=0;}
        }
        return false;
    }

    private Disc getDisc(int x, int y) {
        if(x>=6 || x<0 || y>=7 || y<0)
            return  null;
        else
            return insertDiscArray[x][y];
    }

    public class Disc extends Circle
{
   private final boolean isp1;
   public Disc(boolean isp1){
       System.out.println("Disc Created");
       this.isp1 = isp1;
       setRadius(40);
       setFill(isp1? Color.valueOf(disc1):Color.valueOf(disc2));
       setCenterX(40);
       setCenterY(40);
   }
}
public void clear()
{  p1name.clear();
    p2name.clear();
    playername.setText("PlAYER ONE");
}

   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*Circle c=new Circle();
        c.setRadius(35);
        c.setFill(Color.RED);
        c.setTranslateX(50);
        c.setTranslateY(50);
      pane.getChildren().add(c);
    button.setOnAction(event ->{
        startAnimation(c);
    });*/

       setName.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               player1=p1name.getText();
               player2=p2name.getText();
               playername.setText(player1);
               System.out.println(player1+" "+player2);
           }
       });
    }

   /* private void startAnimation(Circle c) {
        TranslateTransition t=new TranslateTransition(Duration.seconds(2),c);
        //t.setDuration(Duration.seconds(2));
        //t.setNode(c);
        t.setToY(500);

        t.play();

    }*/
}






