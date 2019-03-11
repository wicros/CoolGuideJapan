package jp.co.jpmobile.coolguidejapan.factory;

import java.util.HashMap;
import java.util.Map;

import jp.co.jpmobile.coolguidejapan.base.BaseFragment;
import jp.co.jpmobile.coolguidejapan.fragement.CouponFragement;
import jp.co.jpmobile.coolguidejapan.fragement.FootFragement;
import jp.co.jpmobile.coolguidejapan.fragement.HomeFragement;
import jp.co.jpmobile.coolguidejapan.fragement.VideoFragment;
import jp.co.jpmobile.coolguidejapan.fragement.AppSetFragement;
import jp.co.jpmobile.coolguidejapan.activity.SimRegistActvity;


/**
 * @创建者	 Administrator
 * @创时间 	 2015-8-14 下午3:57:35
 * @描述	     fragment的工厂类
 *
 * @版本       $Rev: 8 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-08-14 17:44:25 +0800 (星期五, 14 八月 2015) $
 * @更新描述    TODO
 */
public class FragmentFactory {

	public static final int						FRAGMENT_FOOT   = 1;
	public static final int						FRAGMENT_HOME		= 0;
	public static final int						FRAGMENT_VEDIO		= 2;
	public static final int						FRAGMENT_COUPON	= 3;
	public static final int						FRAGMENT_APPSET	= 4;

	private static Map<Integer, BaseFragment>	cacheFragmentMap	= new HashMap<Integer, BaseFragment>();

	public static BaseFragment getFragment(int position) {
		BaseFragment fragment = null;
		if (cacheFragmentMap.containsKey(position)) {
			fragment = cacheFragmentMap.get(position);
			return fragment;
		}
		switch (position) {
		case FRAGMENT_HOME:
			fragment = new HomeFragement();
			break;
		case FRAGMENT_FOOT:
			fragment = new FootFragement();
			break;
		case FRAGMENT_VEDIO:
			fragment = new VideoFragment();
			break;
		case FRAGMENT_COUPON:
			fragment = new CouponFragement();
			break;
		case FRAGMENT_APPSET:
			fragment = new AppSetFragement();
			break;


		default:
			break;
		}
		// 进行保存
		cacheFragmentMap.put(position, fragment);
		return fragment;
	}

}
