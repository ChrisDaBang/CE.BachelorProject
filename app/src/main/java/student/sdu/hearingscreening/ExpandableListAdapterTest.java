package student.sdu.hearingscreening;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * Created by Bogs on 08-02-2017.
 */

public class ExpandableListAdapterTest extends BaseExpandableListAdapter {
    private Context context;
    private String[][] contents;
    private String[] tests;

    public ExpandableListAdapterTest(Context context, String[][] contents, String[] tests) {
        super();
        this.context = context;
        this.contents = contents;
        this.tests = tests;
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return contents[groupPosition][childPosition];
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        TextView row = (TextView)convertView;
        if(row == null) {
            row = new TextView(context);
        }
        row.setText(contents[groupPosition][childPosition]);
        return row;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return contents[groupPosition].length;
    }
    @Override
    public String[] getGroup(int groupPosition) {
        return contents[groupPosition];
    }
    @Override
    public int getGroupCount() {
        return contents.length;
    }
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        TextView row = (TextView)convertView;
        if(row == null) {
            row = new TextView(context);
        }
        row.setTypeface(Typeface.DEFAULT_BOLD);
        row.setText(tests[groupPosition]);
        return row;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
