package jp.co.jpmobile.coolguidejapan.base;



import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.lidroid.xutils.util.LogUtils;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.utils.UIUtils;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-8-14 下午4:32:32
 * @描述	     应用视图展示和数据加载的控制类
 *
 * @版本       $Rev: 8 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-08-14 17:44:25 +0800 (星期五, 14 八月 2015) $
 * @更新描述    TODO
 */
public abstract class LoadingPager extends FrameLayout {
	private View			mLoadingView;
	private View			mErrorView;
	private View			mEmptyView;
	private View			mSuccessView;

	public static final int	STYLE_MAIN		= 1;			// 默认状态
	public static final int	STYLE_BACK	= 0;

	private int threadStyle = 0;

	public static final int	STATE_NONE		= -1;			// 默认状态
	public static final int	STATE_LOADING	= 0;			// 加载中

	public static final int	STATE_ERROR		= 1;			// 错误
	public static final int	STATE_EMPTY		= 2;			// 空
	public static final int	STATE_SUCCESS	= 3;			// 成功

	public int				mCurState		= STATE_NONE;

	/**
	 任何应用其实就只有4种页面类型-->常规的页面
	 ① 加载页面
	 ② 错误页面  -->常规的页面
	 ③ 空页面 		
	 		
	 ④ 成功页面 	
		①②③三种页面一个应用基本是固定的
		每一个fragment/activity对应的页面④就不一样
		进入应用的时候显示①,②③④需要加载数据之后才知道显示哪个		
	*/
	public LoadingPager(Context context) {
		super(context);
		initCommonView();

	}

	/**
	 * @des 初始化常规视图① 加载页面② 错误页面③ 空页面
	 * @call LoadingPager初始化的时候被调用
	 */
	private void initCommonView() {
		// ① 加载页面
		mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
		this.addView(mLoadingView);

		// ② 错误页面
		mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
		this.addView(mErrorView);
		// ③ 空页面
		mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
		this.addView(mEmptyView);

		refreshUIByState();
	}

	/**
	 * @des 根据当前的状态显示不同的视图
	 * @call 1.LoadingPager初始化的时候被调用
	 * @call 2.正在开始加载前,重置当前状态,会刷新ui
	 * @call 3.数据加载完成之后被调用
	 */
	private void refreshUIByState() {
		// 控制加载页面的显示/隐藏
		mLoadingView.setVisibility((mCurState == STATE_LOADING) || (mCurState == STATE_NONE) ? View.VISIBLE : View.GONE);

		// 控制错误页面的显示/隐藏
		mErrorView.setVisibility((mCurState == STATE_ERROR) ? View.VISIBLE : View.GONE);

		// 控制空页面的显示/隐藏
		mEmptyView.setVisibility((mCurState == STATE_EMPTY) ? View.VISIBLE : View.GONE);

		if (mSuccessView == null && mCurState == STATE_SUCCESS) {
			mSuccessView = initSuccessView();
			this.addView(mSuccessView);
		}

		if (mSuccessView != null) {
			// 控制成功页面的显示/隐藏
			mSuccessView.setVisibility((mCurState == STATE_SUCCESS) ? View.VISIBLE : View.GONE);
		}
	}

	// 数据加载的流程
	/**
	① 触发加载  	进入页面开始加载/点击某一个按钮的时候加载
	② 异步加载数据  -->显示加载视图
	③ 处理加载结果
		① 成功-->显示成功视图
		② 失败
			① 数据为空-->显示空视图
			② 数据加载失败-->显示加载失败的视图
	*/

	// ① 触发加载
	public void triggerLoadData() {
		if (mCurState != STATE_SUCCESS && mCurState != STATE_LOADING) {

			// ② 异步加载数据
			// 加载开始前,重置状态为加载中
			int state = STATE_LOADING;
			mCurState = state;
			refreshUIByState();

			int tempState_m = initData().getState();
			// 临时状态==当前的状态
			mCurState = tempState_m;

			refreshUIByState();
			//new Thread(new LoadDataTask()).start();
		}
	}



	class LoadDataTask implements Runnable {
		@Override
		public void run() {// 子线程中
			// 真正的开始加载数据
			// 加载数据数据之后-->返回一个临时状态
			int tempState = initData().getState();
			// 临时状态==当前的状态
			mCurState = tempState;
			UIUtils.postTaskSafely(new Runnable() {

				@Override
				public void run() {
					// 再次刷新视图显示
					refreshUIByState();

				}
			});
		}
	}



	/**
	 * @des 真正子子线程中加载数据,必须实现,但是不知道具体实现,交给子类去实现
	 * @call triggerLoadData方法被调用的时候调用
	 */
	protected abstract LoadedResult initData();

	/**
	 * @des 数据加载成功的时候返回具体的成功视图,必须实现,但是不知道具体实现,交给子类实现
	 * @call 数据加载成功的时候
	 */
	protected abstract View initSuccessView();

	public enum LoadedResult {
		SUCCESS(STATE_SUCCESS), EMPTY(STATE_EMPTY), ERROR(STATE_ERROR);

		int	mState;

		public int getState() {
			return mState;
		}

		private LoadedResult(int state) {
			mState = state;
		}
	}
}
