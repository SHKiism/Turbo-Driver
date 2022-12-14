package ir.transport_x.taxi.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import androidx.annotation.IdRes;

/**
 * @author diego
 */
public class GridRadioGroup extends TableLayout implements OnClickListener {

  private static final String TAG = GridRadioGroup.class.getSimpleName();
  private RadioButton activeRadioButton;

  /**
   * @param context
   */
  public GridRadioGroup(Context context) {
    super(context);
  }

  /**
   * @param context
   * @param attrs
   */
  public GridRadioGroup(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public void onClick(View v) {
    final RadioButton rb = (RadioButton) v;
    if (activeRadioButton != null) {
      activeRadioButton.setChecked(false);
    }
    rb.setChecked(true);
    activeRadioButton = rb;

    if (listener != null)
      listener.onItemClick(getCheckedRadioButtonId());
  }

  /* (non-Javadoc)
   * @see android.widget.TableLayout#addView(android.view.View, int, android.view.ViewGroup.LayoutParams)
   */
  @Override
  public void addView(View child, int index,
                      android.view.ViewGroup.LayoutParams params) {
    super.addView(child, index, params);
    setChildrenOnClickListener((TableRow) child);

  }


  /* (non-Javadoc)
   * @see android.widget.TableLayout#addView(android.view.View, android.view.ViewGroup.LayoutParams)
   */
  @Override
  public void addView(View child, android.view.ViewGroup.LayoutParams params) {
    super.addView(child, params);
    setChildrenOnClickListener((TableRow) child);
  }


  private void setChildrenOnClickListener(TableRow tr) {
    final int c = tr.getChildCount();
    for (int i = 0; i < c; i++) {
      final View v = tr.getChildAt(i);
      if (v instanceof RadioButton) {
        v.setOnClickListener(this);

      }
    }
  }

  public int getCheckedRadioButtonId() {
    if (activeRadioButton != null) {
      return activeRadioButton.getId();
    }

    return -1;
  }

  public interface OnClick {
    void onItemClick(int selectedId);
  }

  OnClick listener = null;

  public void setOnItemClickListener(OnClick listener) {
    this.listener = listener;
  }

  public void check(@IdRes int id) {
    RadioButton rb = findViewById(id);
    rb.setChecked(true);
    activeRadioButton = rb;
  }


}