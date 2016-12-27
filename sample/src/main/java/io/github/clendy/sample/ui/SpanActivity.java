package io.github.clendy.sample.ui;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;

import java.util.List;

import butterknife.BindView;
import io.github.clendy.leanback.decoration.HeaderDecoration;
import io.github.clendy.leanback.widget.BaseGridView;
import io.github.clendy.leanback.widget.OnChildLaidOutListener;
import io.github.clendy.leanback.widget.OnChildSelectedListener;
import io.github.clendy.leanback.widget.OnLoadMoreListener;
import io.github.clendy.leanback.widget.SpanGridView;
import io.github.clendy.sample.R;
import io.github.clendy.sample.adapter.LoadMoreAdapter;
import io.github.clendy.sample.adapter.OnItemClickListener;
import io.github.clendy.sample.model.Entity;
import io.github.clendy.sample.presenter.VerticalPresenter;
import io.github.clendy.sample.presenter.VerticalPresenterImpl;
import io.github.clendy.sample.ui.base.BaseFragmentActivity;
import io.github.clendy.sample.view.IView;

/**
 * @author Clendy
 * @date 2016/12/23 023 14:37
 * @e-mail yc330483161@outlook.com
 */
public class SpanActivity extends BaseFragmentActivity<VerticalPresenter> implements IView,
        View.OnFocusChangeListener, View.OnClickListener {

    private static final String TAG = VerticalActivity.class.getSimpleName();

    @BindView(R.id.Button1)
    Button mButton1;
    @BindView(R.id.Button2)
    Button mButton2;
    @BindView(R.id.Button3)
    Button mButton3;
    @BindView(R.id.Button4)
    Button mButton4;
    @BindView(R.id.Button5)
    Button mButton5;
    @BindView(R.id.Button6)
    Button mButton6;
    @BindView(R.id.Button7)
    Button mButton7;
    @BindView(R.id.recyclerView)
    SpanGridView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBarCircularIndeterminate mProgressBar;

    private View mOldFocusView;
    private SparseArray<Button> mBtnArray;
    private LoadMoreAdapter mAdapter;
    private VerticalPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span);

        initView();
        initRequest();
    }

    @Override
    public void response(List<Entity> entities) {
        if (entities == null || entities.size() == 0) {
            return;
        }

        if (mAdapter.getItems().size() == 0) {
            mAdapter.setItems(entities);
        } else {
            mAdapter.addItems(entities);
        }

    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public boolean isOnFinishing() {
        return isFinishing();
    }

    private void initView() {
        initRecyclerView();

        mButton7.setOnClickListener(this);

        mBtnArray = new SparseArray<>(7);
        mBtnArray.append(0, mButton1);
        mBtnArray.append(1, mButton2);
        mBtnArray.append(2, mButton3);
        mBtnArray.append(3, mButton4);
        mBtnArray.append(4, mButton5);
        mBtnArray.append(5, mButton6);
        mBtnArray.append(6, mButton7);
        for (int i = 0; i < mBtnArray.size(); i++) {
            mBtnArray.get(i).setOnFocusChangeListener(this);
        }

    }

    private void initRecyclerView() {

        mAdapter = new LoadMoreAdapter(this);
        mAdapter.setClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object entity) {

            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4,
                GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mRecyclerView.setOnKeyInterceptListener(new SpanGridView.OnKeyInterceptListener() {
            @Override
            public boolean onInterceptKeyEvent(KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (event.getKeyCode()) {
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            if (mRecyclerView.isFocusOnLeftmostColumn()) {
                                if (mOldFocusView != null) {
                                    mOldFocusView.requestFocus();
                                } else {
                                    mBtnArray.get(0).requestFocus();
                                }
                                return true;
                            }
                            return false;
                        case KeyEvent.KEYCODE_DPAD_UP:
                            return false;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            return false;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            return false;
                        default:
                            return false;
                    }

                }
                return false;
            }
        });
    }

    private void initRequest() {
        mAdapter.getItems().clear();
        mAdapter.notifyDataSetChanged();
        mPresenter = new VerticalPresenterImpl(this);
        mPresenters.add(mPresenter);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            switch (v.getId()) {
                case R.id.Button1:
                    mOldFocusView = mButton1;
                    break;
                case R.id.Button2:
                    mOldFocusView = mButton2;
                    break;
                case R.id.Button3:
                    mOldFocusView = mButton3;
                    break;
                case R.id.Button4:
                    mOldFocusView = mButton4;
                    break;
                case R.id.Button5:
                    mOldFocusView = mButton5;
                    break;
                case R.id.Button6:
                    mOldFocusView = mButton6;
                    break;
                case R.id.Button7:
                    mOldFocusView = mButton7;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mOldFocusView != null && !mOldFocusView.hasFocus()) {
                    mOldFocusView.requestFocus();
                    return true;
                }
                return super.onKeyDown(keyCode, event);
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Button7:
                initRequest();
                break;
            default:
                break;
        }

    }
}
