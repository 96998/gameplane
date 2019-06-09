package com.ispring.gameplane.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 奖品,奖励的运动轨迹与销毁情况　
 */
public class Award extends AutoSprite {
    public static int STATUS_DOWN1 = 1;
    public static int STATUS_UP2 = 2;
    public static int STATUS_DOWN3 = 3;

    private int status = STATUS_DOWN1;

    public Award(Bitmap bitmap){
        super(bitmap);
        setSpeed(7);            //初始速度为每帧7像素
    }

    @Override
    protected void afterDraw(Canvas canvas, Paint paint, GameView gameView) {
        //在afterDraw中不调用super.afterDraw方法
        if(!isDestroyed()){
            //在绘制一定次数后要改变方向或速度
            int canvasHeight = canvas.getHeight();
            if(status != STATUS_DOWN3){
                float maxY = getY() + getHeight();
//                float maxX = getX() + getWidth();
                if(status == STATUS_DOWN1){
                    //第一次向下
                    if(maxY >= canvasHeight * 0.5){
                        //当第一次下降到临界值时改变方向，向上,设置临界值为屏幕的一半处
                        setSpeed(-5);
                        status = STATUS_UP2;
                    }
//                    //第一次向右
//                    if(maxX>=canvas.getWidth()){
//                        setSpeed(-5);
//                        status = STATUS_UP2;
//                    }
                }
                else if(status == STATUS_UP2){
                    //第二次向上
                    if(maxY+this.getSpeed() <= 0){
                        //第二次上升到临界值时改变方向，向下
                        setSpeed(8);            //在最后一次以每帧8像素向下运动
                        status = STATUS_DOWN3;  //切换状态为第三次向下
                    }
                }
            }
            if(status == STATUS_DOWN3){         //如果最后一次下去也没有被捡拾的话就销毁
                if(getY() >= canvasHeight){
                    destroy();
                }
            }
        }
    }
}