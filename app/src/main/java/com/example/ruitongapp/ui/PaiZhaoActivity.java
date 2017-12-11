package com.example.ruitongapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruitongapp.MyApplication;
import com.example.ruitongapp.R;
import com.example.ruitongapp.beans.BaoCunBean;
import com.example.ruitongapp.beans.BaoCunBeanDao;
import com.example.ruitongapp.beans.ShouFangBean;
import com.example.ruitongapp.dialogs.TiJIaoDialog;
import com.example.ruitongapp.utils.FileUtil;
import com.example.ruitongapp.utils.GsonUtil;
import com.example.ruitongapp.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;
import com.tzutalin.dlib.FaceDet;
import com.tzutalin.dlib.VisionDetRet;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PaiZhaoActivity extends Activity implements SurfaceHolder.Callback {


    @BindView(R.id.qiehuan)
    TextView qiehuan;
    @BindView(R.id.paizhao)
    Button paizhao;
    private String TAG = "paizhao";
    private Button baocun;

    private SurfaceView surfaceView;
    private Camera mCamera;
    private SurfaceHolder sh;
    private FaceDet mFaceDet;
    private Bitmap bmp2 = null;
    private static boolean isTrue3 = true;
    private static boolean isTrue4 = false;
    private static int count = 1;
    private int cameraID=0;
    private String paths=null;
    private TiJIaoDialog tiJIaoDialog=null;
    private ImageView yulan;
    private RelativeLayout jiazai_rl;
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
    private String ss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pai_zhao);
        baoCunBeanDao=MyApplication.myAppLaction.getDaoSession().getBaoCunBeanDao();
        if (baoCunBeanDao!=null){
            baoCunBean=baoCunBeanDao.load(123456L);
        }
        jiazai_rl= (RelativeLayout) findViewById(R.id.jiazai_rl);
        yulan= (ImageView) findViewById(R.id.yulan);
        ButterKnife.bind(this);
        mFaceDet = MyApplication.mFaceDet;

        surfaceView = (SurfaceView) findViewById(R.id.fff);
        baocun= (Button) findViewById(R.id.baocu);
        baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("paizhang_sb");
                intent.putExtra("path",paths);
                sendBroadcast(intent);
                finish();
            }
        });

        OpenCameraAndSetSurfaceviewSize(cameraID);

    }


    private int getPreviewRotateDegree(int p) {
        int phoneDegree = 0;
        int result = 0;
        //获得手机方向
        int phoneRotate = getWindowManager().getDefaultDisplay().getOrientation();
        //得到手机的角度
        switch (phoneRotate) {
            case Surface.ROTATION_0:
                phoneDegree = 0;
                break;        //0
            case Surface.ROTATION_90:
                phoneDegree = 90;
                break;      //90
            case Surface.ROTATION_180:
                phoneDegree = 180;
                break;    //180
            case Surface.ROTATION_270:
                phoneDegree = 270;
                break;    //270
        }
        //分别计算前后置摄像头需要旋转的角度
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        if (p == 1) {
            Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_FRONT, cameraInfo);
            result = (cameraInfo.orientation + phoneDegree) % 360;
            result = (360 - result) % 360;
        } else {
            Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, cameraInfo);
            result = (cameraInfo.orientation - phoneDegree + 360) % 360;
        }
        return result;
    }


    private Void OpenCameraAndSetSurfaceviewSize(final int cameraId) {

        sh = surfaceView.getHolder();
        sh.addCallback(this);

        mCamera = Camera.open(cameraId);
        int rotateDegree = getPreviewRotateDegree(cameraId);
        mCamera.setDisplayOrientation(rotateDegree);

        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size pre_size = parameters.getPreviewSize();
        //  Camera.Size pic_size = parameters.getPictureSize();
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        try {
            mCamera.setPreviewDisplay(sh);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCamera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, final Camera camera) {
                while (isTrue4) {
                    isTrue4 = false;
                    try {
                        Camera.Size size = camera.getParameters().getPreviewSize();
                        YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        image.compressToJpeg(new Rect(0, 0, size.width, size.height), 100, stream);

                        bmp2 = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());

                        camera.stopPreview();

                        final Matrix matrix = new Matrix();
                        if (cameraId == 1) {
                            matrix.postRotate(270);
                        } else {
                            matrix.postRotate(90);
                        }

                        bmp2 = Bitmap.createBitmap(bmp2, 0, 0, bmp2.getWidth(), bmp2.getHeight(), matrix, true);
                        jiazai_rl.setVisibility(View.VISIBLE);
                        yulan.setVisibility(View.VISIBLE);
                        yulan.setImageBitmap(bmp2);
                        paizhao.setEnabled(false);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                        List<VisionDetRet> results = mFaceDet.detect(bmp2);


                        if (results != null) {

                            int s = results.size();
                            VisionDetRet face;
                            if (s > 0) {
                                if (s > count - 1) {
                                    face = results.get(count - 1);
                                } else {
                                    face = results.get(0);
                                }
                                int xx = 0;
                                int yy = 0;
                                int xx2 = 0;
                                int yy2 = 0;
                                int ww = bmp2.getWidth();
                                int hh = bmp2.getHeight();
                                if (face.getRight() - 890 >= 0) {
                                    xx = face.getRight() - 890;
                                } else {
                                    xx = 0;
                                }
                                if (face.getTop() - 740 >= 0) {
                                    yy = face.getTop() - 740;
                                } else {
                                    yy = 0;
                                }
                                if (xx + 1460 <= ww) {
                                    xx2 = 1460;
                                } else {
                                    xx2 = ww - xx;
                                }
                                if (yy + 1760 <= hh) {
                                    yy2 = 1760;
                                } else {
                                    yy2 = hh - yy;
                                }

                                final Bitmap bitmap = Bitmap.createBitmap(bmp2, xx, yy, xx2, yy2);

                                String fn = "bbbb.jpg";
                                FileUtil.isExists(FileUtil.PATH, fn);
                                saveBitmap2File2(bitmap, FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + fn, 100);

                            } else {
                                isTrue4 = false;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        paizhao.setEnabled(true);
                                        Utils.showToast(PaiZhaoActivity.this, "没有检查到人脸,请重新拍摄", 3);
                                        if (!PaiZhaoActivity.this.isFinishing())
                                        camera.startPreview();
                                      jiazai_rl.setVisibility(View.GONE);
                                    }
                                });


                            }

                        }
                            }


                        }).start();

                    stream.close();

                } catch(Exception ex){
                    Log.e("Sys", "Error:" + ex.getMessage());
                }
                }
            }
        });

        return null;
    }

    public void saveBitmap2File2(Bitmap bm, final String path, int quality) {
        try {
            paths=path;
            if (null == bm) {
                Log.d("InFoActivity", "回收|空");
                return;
            }

            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    paizhao.setVisibility(View.GONE);
                    baocun.setVisibility(View.VISIBLE);
                    jiazai_rl.setVisibility(View.GONE);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            if (!bm.isRecycled()) {
                bm.recycle();
            }
            bm = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        mCamera.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Log.d("PaiZhaoActivity", "改变");
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            // 获取摄像头支持的PictureSize列表
            List<Camera.Size> pictureSizeList = parameters.getSupportedPictureSizes();
            for (Camera.Size size : pictureSizeList) {
                Log.i(TAG, "pictureSizeList size.width=" + size.width + "  size.height=" + size.height);
            }
            /**从列表中选取合适的分辨率*/
            Camera.Size picSize = getProperSize(pictureSizeList, ((float) height / width));
            if (null == picSize) {
              //  Log.i(TAG, "null == picSize");
                picSize = parameters.getPictureSize();
            }
         //   Log.i(TAG, "picSize.width=" + picSize.width + "  picSize.height=" + picSize.height);
            // 根据选出的PictureSize重新设置SurfaceView大小
            float w = picSize.width;
            float h = picSize.height;
            parameters.setPictureSize(picSize.width, picSize.height);
            surfaceView.setLayoutParams(new RelativeLayout.LayoutParams((int) (height * (h / w)), height));

            // 获取摄像头支持的PreviewSize列表
            List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();

            for (Camera.Size size : previewSizeList) {
                Log.i(TAG, "previewSizeList size.width=" + size.width + "  size.height=" + size.height);
            }
            Camera.Size preSize = getProperSize(previewSizeList, ((float) height) / width);
            if (null != preSize) {
               // Log.i(TAG, "preSize.width=" + preSize.width + "  preSize.height=" + preSize.height);
                parameters.setPreviewSize(preSize.width, preSize.height);
            }

            parameters.setJpegQuality(100); // 设置照片质量
            if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 连续对焦模式
            }
            mCamera.cancelAutoFocus();//自动对焦。
            mCamera.setDisplayOrientation(90);// 设置PreviewDisplay的方向，效果就是将捕获的画面旋转多少度显示
            mCamera.setParameters(parameters);
            mCamera.setPreviewDisplay(holder);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        Log.d("InFoActivity3", "surfaceView销毁");

        if (mCamera != null) {
            sh.removeCallback(this);
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }

    }

    /**
     * 从列表中选取合适的分辨率
     * 默认w:h = 4:3
     * <p>注意：这里的w对应屏幕的height
     * h对应屏幕的width<p/>
     */
    private Camera.Size getProperSize(List<Camera.Size> pictureSizeList, float screenRatio) {
        Log.i(TAG, "screenRatio=" + screenRatio);
        Camera.Size result = null;
        for (Camera.Size size : pictureSizeList) {
            float currentRatio = ((float) size.width) / size.height;
            if (currentRatio - screenRatio == 0) {
                result = size;
                break;
            }
        }

        if (null == result) {
            for (Camera.Size size : pictureSizeList) {
                float curRatio = ((float) size.width) / size.height;
                if (curRatio == 4f / 3) {// 默认w:h = 4:3
                    result = size;
                    break;
                }
            }
        }

        return result;
    }


    @OnClick({R.id.qiehuan, R.id.paizhao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qiehuan:
                //切换前后摄像头
                int cameraCount = 0;
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

                for(int i = 0; i < cameraCount; i++) {
                    Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
                    if(cameraID == 0) {
                        //现在是后置，变更为前置
                        if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            sh.removeCallback(this);
                            mCamera.setPreviewCallback(null);
                            mCamera.stopPreview();//停掉原来摄像头的预览
                            mCamera.release();//释放资源
                            mCamera = null;//取消原来摄像头
                            cameraID = 1;

                            OpenCameraAndSetSurfaceviewSize(cameraID);
                            RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) surfaceView.getLayoutParams();
                            layoutParams.width=surfaceView.getWidth()-1;
                            layoutParams.height=surfaceView.getHeight()-1;
                            surfaceView.setLayoutParams(layoutParams);
                            surfaceView.invalidate();
                            mCamera.startPreview();

                            break;
                        }
                    } else {
                        //现在是前置， 变更为后置
                        if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            sh.removeCallback(this);
                            mCamera.setPreviewCallback(null);
                            mCamera.stopPreview();//停掉原来摄像头的预览
                            mCamera.release();//释放资源
                            mCamera = null;//取消原来摄像头
                            cameraID = 0;
                            OpenCameraAndSetSurfaceviewSize(cameraID);

                            RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) surfaceView.getLayoutParams();
                            layoutParams.width=surfaceView.getWidth()-1;
                            layoutParams.height=surfaceView.getHeight()-1;
                            surfaceView.setLayoutParams(layoutParams);
                            surfaceView.invalidate();

                            mCamera.startPreview();

                            break;
                        }
                    }

                }


                break;
            case R.id.paizhao:
                isTrue4=true;

                break;
        }
    }

    private void link_zhiliang(final String path) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tiJIaoDialog==null && !PaiZhaoActivity.this.isFinishing()){
                    tiJIaoDialog=new TiJIaoDialog(PaiZhaoActivity.this);
                    tiJIaoDialog.show();
                }
            }
        });


        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
        OkHttpClient okHttpClient= MyApplication.getOkHttpClient();

        if (null!=baoCunBean.getSid()) {


            //    /* form的分割线,自己定义 */
            //        String boundary = "xx--------------------------------------------------------------xx";
            RequestBody body = new FormBody.Builder()
                    .add("scanPhoto", path)
                    .add("accountId", baoCunBean.getSid())
                    .build();


            Request.Builder requestBuilder = new Request.Builder()
                    // .header("Content-Type", "application/json")
                    .post(body)
                    .url(baoCunBean.getDizhi() + "/faceQuality.do");

            // step 3：创建 Call 对象
            Call call = okHttpClient.newCall(requestBuilder.build());

            //step 4: 开始异步请求
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("AllConnects", "请求识别失败" + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (tiJIaoDialog != null && !PaiZhaoActivity.this.isFinishing() && tiJIaoDialog.isShowing()) {
                                tiJIaoDialog.dismiss();
                                tiJIaoDialog = null;
                            }
                        }
                    });

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (tiJIaoDialog != null) {
                                tiJIaoDialog.dismiss();
                                tiJIaoDialog = null;
                            }
                        }
                    });
                    Log.d("AllConnects", "请求识别成功" + call.request().toString());
                    //获得返回体
                    try {

                        ResponseBody body = response.body();
                        ss = body.string().trim();
                        Log.d("DengJiActivity", ss);

                        JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                        Gson gson = new Gson();
                        ShouFangBean zhaoPianBean = gson.fromJson(jsonObject, ShouFangBean.class);

                        if (zhaoPianBean.getDtoResult() != 0) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (!PaiZhaoActivity.this.isFinishing()) {
                                        Toast tastyToast = TastyToast.makeText(PaiZhaoActivity.this, "照片质量不符合入库要求,请拍正面照!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                        tastyToast.show();
                                    }


                                }
                            });

                        } else {

                        }

                    } catch (Exception e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (tiJIaoDialog != null && !PaiZhaoActivity.this.isFinishing()) {
                                    tiJIaoDialog.dismiss();
                                    tiJIaoDialog = null;
                                }
                            }
                        });
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast tastyToast = TastyToast.makeText(PaiZhaoActivity.this, "提交失败,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();

                            }
                        });
                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });
        }else {
            Toast tastyToast = TastyToast.makeText(PaiZhaoActivity.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }


    }
}
