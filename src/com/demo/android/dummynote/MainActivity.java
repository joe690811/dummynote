package com.demo.android.dummynote;





import android.R.menu;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity{

	

	private int mNotesNumber = 1;
	protected  static final int MENU_INSERT = Menu.FIRST;
	protected  static final int MENU_DELETE = Menu.FIRST +1;
	private DB mDbHelper;
	private Cursor mNotesCursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//設定呈現畫面的地方來自於 	activity_main 
		//setContentView(R.layout.activity_main);
		//若不設定要看原本的活動有沒有 保定一個顯示的介面 ListActivity 是有綁定的
	
		//getListView().setEmptyView(findViewById(R.id.empty));
		setAdapter();
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add(0,MENU_INSERT,0,"新增記事");
		menu.add(0,MENU_DELETE,0,"刪除記事");
		return super.onCreateOptionsMenu(menu);
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case MENU_INSERT:
			String noteName = "Note " + mNotesNumber++;
			mDbHelper.create(noteName);
			fillData();
			//break;
		case MENU_DELETE:
			//取得getListView ID值得方法
			openOptionsDialog(getListView().getSelectedItemPosition()+10);
			mDbHelper.delete(getListView().getSelectedItemPosition()+10);
			fillData();
		}
		return super.onOptionsItemSelected(item);
	}

	private void openOptionsDialog(long test1) {

		// 具名實體 對話框
		 AlertDialog.Builder builder = new
		 AlertDialog.Builder(MainActivity.this);
		 builder.setTitle("關於 Android BMI");
		 builder.setMessage(test1+" ");
		 builder.show();

	
				

	}
	private String[]  note_array ={
		
		"joe",
		"crota",
		"louk",
		"magicion"
			
	};
	
	
	private void setAdapter(){
		//比較花記憶體的寫法 資料來源使用陣列資源檔案
//		ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,note_array);
//		setListAdapter(adapter);

		//比較省記憶體的寫法  資料來源使用陣列資源檔案
		//setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,note_array));
		
		//使用 sqlite 資料來源儲存資料
		mDbHelper = new DB(this);
		mDbHelper.open();
		fillData();
	}
	private void fillData() {
		// TODO Auto-generated method stub
		mNotesCursor = mDbHelper.getAll();
		startManagingCursor(mNotesCursor);
		
		String[] from  = new String[]{DB.KEY_NOTE};
		int[]  to = new int[] {android.R.id.text1};
		
		//NOW create a simple cursor adapters
		SimpleCursorAdapter adapter  = new 	SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,mNotesCursor,from,to);
		setListAdapter(adapter);
	}
	
}
