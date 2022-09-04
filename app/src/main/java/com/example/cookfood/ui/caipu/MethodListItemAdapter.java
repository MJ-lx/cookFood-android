package com.example.cookfood.ui.caipu;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.cookfood.CaipuDetail;
import com.example.cookfood.MainActivity;
import com.example.cookfood.R;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class MethodListItemAdapter extends BaseAdapter {
    private final Context context;
    private List<MethodClass> data;
    private static final String[] strings = {"一","二","三","四","五","六","七","八","九","十","十一","十二","十三"};
    ViewHolder holder = null;

    public MethodListItemAdapter(List<MethodClass> data, Context context){
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MethodClass methods = data.get(position);
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.method_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        }
        else {
            holder = (MethodListItemAdapter.ViewHolder) convertView.getTag();
        }
        //清除焦点
        holder.content.clearFocus();

        //先清除之前的文本改变监听
        if (holder.content.getTag() instanceof TextWatcher) {
            holder.content.removeTextChangedListener((TextWatcher) (holder.content.getTag()));
        }

        //设置数据
        holder.content.setText(TextUtils.isEmpty(methods.content)? "":methods.content);
        holder.imgBtn.setImageURI(TextUtils.isEmpty(methods.img)? null: Uri.parse(methods.img));

        //文本改变监听
        final TextWatcher ContentWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    methods.content = null;
                } else {
                    methods.content = String.valueOf(s);
                }
            }
        };
        //把监听设置到不同的EditText上
        holder.content.addTextChangedListener(ContentWatcher);
        holder.content.setTag(ContentWatcher);

        //点击按钮选择相册照片
        holder.imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //动态申请权限
                if (ContextCompat.checkSelfPermission(context, Manifest.permission
                        .WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions((Activity)context,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    //执行启动相册的方法
                    openAlbum();
                }
            }
        });

        //配置数据
        TextView textview = convertView.findViewById(R.id.method_text);
        textview.setText("第"+strings[position]+"步");

        return convertView;
    }
    //获取权限的结果
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if (grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED) openAlbum();
            else Toast.makeText(context,"你拒绝了",Toast.LENGTH_SHORT).show();
        }
    }

    //启动相册的方法
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        ActivityCompat.startActivityForResult((Activity)context,intent,2,null);
    }

    public final class ViewHolder {
        public EditText content;// ListView中的输入
        public ImageButton imgBtn;// ListView中的输入
        public ViewHolder(View convertView){
            content = (EditText) convertView.findViewById(R.id.method1);
            imgBtn = (ImageButton) convertView.findViewById(R.id.caipu_imgbtn1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2){
            //判断安卓版本
            if (resultCode == RESULT_OK&&data!=null){
                if (Build.VERSION.SDK_INT>=19)
                    handImage(data);
                else handImageLow(data);
            }
        }
    }

    //安卓版本大于4.4的处理方法
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handImage(Intent data){
        String path =null;
        Uri uri = data.getData();
        //根据不同的uri进行不同的解析
        if (DocumentsContract.isDocumentUri(context,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID+"="+id;
                path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                path = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            path = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            path = uri.getPath();
        }
        //展示图片
        displayImage(path);
    }


    //安卓小于4.4的处理方法
    private void handImageLow(Intent data){
        Uri uri = data.getData();
        String path = getImagePath(uri,null);
        displayImage(path);
    }

    //content类型的uri获取图片路径的方法
    private String getImagePath(Uri uri,String selection) {
        String path = null;
        Cursor cursor =context.getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //根据路径展示图片的方法
    private void displayImage(String imagePath){
        if (imagePath != null){
            holder.imgBtn.setImageURI(Uri.parse(imagePath));
        }else{
            Toast.makeText(context,"fail to set image",Toast.LENGTH_SHORT).show();
        }
    }

}
