package com.example.core.http.model;

public class ComRetCode {
	/* 新接口返回属性 */
	public static final String RET_CODE = "retCode";
	public static final String RET_DESC = "retDesc";
	public static final String RET_FIELD = "retField";
	public static final String RET = "ret";
	
	public static final String PAGINTOR = "paginator";
	public static final String PAGINTOR_ITEM_LIST = "pageItemList";

	/**
	 * 200 成功
	 */
	public static final int SUCCESS = 200;
	public static final String SUCCESS_DESC = "操作成功";

	
	/** #20 通用错误 **/

	/* 201请不要重复提交请求 */
	public static final int SUCCESS_DUPLICATED = 201;
	public static final String SUCCESS_DUPLICATED_DESC = "请不要重复提交请求";

	/* 202网络异常，请稍后再试！ */
	public static final int FAIL = 202;
	public static final String FAIL_DESC = "网络异常，请稍后再试！";

	/* 205系统错误 */
	public static final int COMMON_FAIL = 205;
	public static final String COMMON_FAIL_DESC = "系统错误";

	/* 203网络异常，请稍后再试！ */
	public static final int FAIL_PARTNER = 203;
	public static final String FAIL_PARTNER_DESC = "网络异常，请稍后再试！";

	/* 204网络异常，请稍后再试！ */
	public static final int FAIL_DB = 204;
	public static final String FAIL_DB_DESC = "网络异常，请稍后再试！";


	/** #21 登录相关错误 **/

	/* 211用户已存在。 */
	public static final int USER_EXIST = 211;
	public static final String USER_EXIST_DESC = "用户已存在。";

	/* 212用户不存在。 */
	public static final int USER_NOT_EXIST = 212;
	public static final String USER_NOT_EXIST_DESC = "用户不存在。";

	/* 213用户名或密码错误。 */
	public static final int LOGIN_FAIL = 213;
	public static final String LOGIN_FAIL_DESC = "用户名或密码错误。";

	/* 214帐号未登录，请登录后再操作 */
	public static final int NOT_LOGIN = 214;
	public static final String NOT_LOGIN_DESC = "帐号未登录，请登录后再操作";

	/* 215登录已超时 */
	public static final int LOGIN_TIMEOUT = 215;
	public static final String LOGIN_TIMEOUT_DESC = "登录已超时";

	/* 216登录状态已失效，请重新登录 */
	public static final int TOKEN_INVALID = 216;
	public static final String TOKEN_INVALID_DESC = "登录状态已失效，请重新登录";

	/* 217您的帐号已锁定，请联系客服解除 */
	public static final int USER_LOCKED = 217;
	public static final String USER_LOCKED_DESC = "您的帐号已锁定，请联系客服解除";

	/* 218邮箱已存在，请重新填写 */
	public static final int USER_EMAIL_EXIST = 218;
	public static final String USER_EMAIL_EXIST_DESC = "邮箱已存在，请重新填写";

	/* 219你还没有填写昵称，请填写后操作 */
	public static final int USER_NOT_COMPLETE_INFO_ERROR = 219;
	public static final String USER_NOT_COMPLETE_INFO_ERROR_DESC = "你还没有填写昵称，请填写后操作";

	/* 220微信登录失败。 */
	public static final int WEIXIN_LOGIN_FAIL = 220;
	public static final String WEIXIN_LOGIN_FAIL_DESC = "微信登录失败。";


	/** #26 流程错误 **/

	/* 261手机验证已超时，请重新验证 */
	public static final int VERIFY_MOBILE_TIMEOUT = 261;
	public static final String VERIFY_MOBILE_TIMEOUT_DESC = "手机验证已超时，请重新验证";

	/* 262订单编号错误 */
	public static final int PARAM_SERIALNO_WRONG = 262;
	public static final String PARAM_SERIALNO_WRONG_DESC = "订单编号错误";

	/* 263请填写正确的短信验证码 */
	public static final int SMS_CODE_WRONG = 263;
	public static final String SMS_CODE_WRONG_DESC = "请填写正确的短信验证码";


	/** #30 通用格式错误 **/

	/* 301请求参数错误，请稍后重试 */
	public static final int WRONG_PARAMETER = 301;
	public static final String WRONG_PARAMETER_DESC = "请求参数错误，请稍后重试";

	/* 302IP校验失败 */
	public static final int IP_NOT_ALLOW = 302;
	public static final String IP_NOT_ALLOW_DESC = "IP校验失败";


	/** #31 输入格式错误 **/

	/* 311原密码错误。 */
	public static final int OLD_PASSWORD_ERROR = 311;
	public static final String OLD_PASSWORD_ERROR_DESC = "原密码错误。";

	/* 312密码格式错误 */
	public static final int WRONG_PASSOWRD = 312;
	public static final String WRONG_PASSOWRD_DESC = "密码格式错误";

	/* 313请您填写正确的手机号 */
	public static final int WRONG_MOBILE = 313;
	public static final String WRONG_MOBILE_DESC = "请您填写正确的手机号";

	/* 314请您填写正确的姓名 */
	public static final int NAME_FORMAT_ERROR = 314;
	public static final String NAME_FORMAT_ERROR_DESC = "请您填写正确的姓名";

	/* 315请您填写正确的手机号 */
	public static final int MOBILE_FORMAT_ERROR = 315;
	public static final String MOBILE_FORMAT_ERROR_DESC = "请您填写正确的手机号";

	/* 316请您填写正确的身份证号 */
	public static final int IDNO_FORMAT_ERROR = 316;
	public static final String IDNO_FORMAT_ERROR_DESC = "请您填写正确的身份证号";

	/* 317请您填写正确的邮箱 */
	public static final int EMAIL_FORMAT_ERROR = 317;
	public static final String EMAIL_FORMAT_ERROR_DESC = "请您填写正确的邮箱";

	/* 337请您填写正确的生日 */
	public static final int BIRTHDAY_FORMAT_ERROR = 337;
	public static final String BIRTHDAY_FORMAT_ERROR_DESC = "请您填写正确的生日";

	/* 338请您选择性别 */
	public static final int GENDER_FORMAT_ERROR = 338;
	public static final String GENDER_FORMAT_ERROR_DESC = "请您选择性别";

	/* 339您填写的姓名中包含敏感词 */
	public static final int NAME_MINGAN_ERROR = 339;
	public static final String NAME_MINGAN_ERROR_DESC = "您填写的姓名中包含敏感词";

	/* 318您填写的邀请码不正确，请核实后填写 */
	public static final int REGISTER_INVITE_CODE_ERROR = 318;
	public static final String REGISTER_INVITE_CODE_ERROR_DESC = "您填写的邀请码不正确，请核实后填写";

	/* 319您填写的邀请码已使用 */
	public static final int REGISTER_INVITE_CODE_USED_ERROR = 319;
	public static final String REGISTER_INVITE_CODE_USED_ERROR_DESC = "您填写的邀请码已使用";

	/* 320上传头像图片错误 */
	public static final int UPLOAD_AVATAR_ERROR = 320;
	public static final String UPLOAD_AVATAR_ERROR_DESC = "上传头像图片错误";

	/* 321邮件验证链接已过期 */
	public static final int EMAIL_VERIFY_ERROR = 321;
	public static final String EMAIL_VERIFY_ERROR_DESC = "邮件验证链接已过期";


	/** #40 彩票相关 **/

	/* 401彩种不存在 */
	public static final int GAME_NOT_EXIST_ERROR = 401;
	public static final String GAME_NOT_EXIST_ERROR_DESC = "彩种不存在";

	/* 402彩种未开放 */
	public static final int GAME_NOT_OPEN_ERROR = 402;
	public static final String GAME_NOT_OPEN_ERROR_DESC = "彩种未开放";

	/* 403期次不存在 */
	public static final int PERIOD_NOT_EXIST_ERROR = 403;
	public static final String PERIOD_NOT_EXIST_ERROR_DESC = "期次不存在";

	/* 404彩种售卖期次有误! */
	public static final int PERIOD_NOT_VALID_ERROR = 404;
	public static final String PERIOD_NOT_VALID_ERROR_DESC = "彩种售卖期次有误!";

	/* 407当前期次还未开售 */
	public static final int PERIOD_NOT_OPEN_ERROR = 407;
	public static final String PERIOD_NOT_OPEN_ERROR_DESC = "当前期次还未开售";

	/* 410期次已经改变 */
	public static final int PERIOD_CHANGE_ERROR = 410;
	public static final String PERIOD_CHANGE_ERROR_DESC = "期次已经改变";

	/* 417期次已经改变,当前订单已关闭，请重新投注 */
	public static final int PERIOD_CHANGE_CLOSE_ERROR = 417;
	public static final String PERIOD_CHANGE_CLOSE_ERROR_DESC = "期次已经改变,当前订单已关闭，请重新投注";

	/* 405春节（除夕-初六）休市，初七（2月3日）开市哟！ */
	public static final int SPRING_CLOSE_ERROR = 405;
	public static final String SPRING_CLOSE_ERROR_DESC = "春节（除夕-初六）休市，初七（2月3日）开市哟！";

	/* 406已暂停售彩，请稍后再试 */
	public static final int GAME_STOP_ERROR = 406;
	public static final String GAME_STOP_ERROR_DESC = "已暂停售彩，请稍后再试";

	/* 408订单参数错误 */
	public static final int ORDER_PARAMETER_ERROR = 408;
	public static final String ORDER_PARAMETER_ERROR_DESC = "订单参数错误";

	/* 409订单状态错误 */
	public static final int ORDER_STATUS_ERROR = 409;
	public static final String ORDER_STATUS_ERROR_DESC = "订单状态错误";

	/* 411追号不存在 */
	public static final int FOLLOW_NOT_EXIST_ERROR = 411;
	public static final String FOLLOW_NOT_EXIST_ERROR_DESC = "追号不存在";

	/* 412追号参数错误 */
	public static final int FOLLOW_PARAMETER_ERROR = 412;
	public static final String FOLLOW_PARAMETER_ERROR_DESC = "追号参数错误";

	/* 413追号状态错误 */
	public static final int FOLLOW_STATUS_ERROR = 413;
	public static final String FOLLOW_STATUS_ERROR_DESC = "追号状态错误";

	/* 414您选择的红包不存在 */
	public static final int REDPACKET_NOT_EXIST_ERROR = 414;
	public static final String REDPACKET_NOT_EXIST_ERROR_DESC = "您选择的红包不存在";

	/* 415您选择的红包不可用 */
	public static final int REDPACKET_NOT_AVAILABLE_ERROR = 415;
	public static final String REDPACKET_NOT_AVAILABLE_ERROR_DESC = "您选择的红包不可用";

	/* 416您选择红包金额余额不足，当前订单已关闭，请重新投注 */
	public static final int REDPACKET_NOT_ENOUGH_ERROR = 416;
	public static final String REDPACKET_NOT_ENOUGH_ERROR_DESC = "您选择红包金额余额不足，当前订单已关闭，请重新投注";


	/** #80 次数限制错误 **/

	/* 501您获取短信验证码次数过多，请明天再试 */
	public static final int SMS_SEND_TIME_LIMIT_OVER = 501;
	public static final String SMS_SEND_TIME_LIMIT_OVER_DESC = "您获取短信验证码次数过多，请明天再试";

	/* 502您输入短信验证码错误次数过多，请明天再试 */
	public static final int SMS_VERIFY_TIME_LIMIT_OVER = 502;
	public static final String SMS_VERIFY_TIME_LIMIT_OVER_DESC = "您输入短信验证码错误次数过多，请明天再试";

	/* 503您输入密码错误次数过多，请明天再试 */
	public static final int PWD_LOGIN_FAIL_TIME_LIMIT_OVER = 503;
	public static final String PWD_LOGIN_FAIL_TIME_LIMIT_OVER_DESC = "您输入密码错误次数过多，请明天再试";


	/** #60 支付流程相关错误 **/

	/* 601商品不存在 */
	public static final int SKU_NOT_EXIST_ERROR = 601;
	public static final String SKU_NOT_EXIST_ERROR_DESC = "商品不存在";

	/* 329订单不存在 */
	public static final int ORDER_NOT_EXIST_ERROR = 329;
	public static final String ORDER_NOT_EXIST_ERROR_DESC = "订单不存在";

	/* 602订单已存在 */
	public static final int ORDER_EXIST = 602;
	public static final String ORDER_EXIST_DESC = "订单已存在";

	/* 326锁库存失败 */
	public static final int ORDER_LOCK_STOCK_FAIL_ERROR = 326;
	public static final String ORDER_LOCK_STOCK_FAIL_ERROR_DESC = "锁库存失败";

	/* 327持久化订单失败 */
	public static final int ORDER_SAVE_ORDER_FAIL_ERROR = 327;
	public static final String ORDER_SAVE_ORDER_FAIL_ERROR_DESC = "持久化订单失败";

	/* 328更新订单失败 */
	public static final int ORDER_UPDATE_ORDER_FAIL_ERROR = 328;
	public static final String ORDER_UPDATE_ORDER_FAIL_ERROR_DESC = "更新订单失败";

	/* 335通知合作方发货失败 */
	public static final int ORDER_DELIVER_ERROR = 335;
	public static final String ORDER_DELIVER_ERROR_DESC = "通知合作方发货失败";

	/* 603商户平台不存在（或无效） */
	public static final int PLATFORM_NOT_EXIST = 603;
	public static final String PLATFORM_NOT_EXIST_DESC = "商户平台不存在（或无效）";

	/* 604商户平台无权限调用 */
	public static final int PLATFORM_NO_POWER = 604;
	public static final String PLATFORM_NO_POWER_DESC = "商户平台无权限调用";

	/* 605签名错误 */
	public static final int WRONG_SIGN = 605;
	public static final String WRONG_SIGN_DESC = "签名错误";

	/* 606退款订单状态异常，不容许退款 */
	public static final int REFUND_ORDER_CAN_NOT_REFUND = 606;
	public static final String REFUND_ORDER_CAN_NOT_REFUND_DESC = "退款订单状态异常，不容许退款";

	/* 607退款订单已经存在，但是重复提交的明细不一致 */
	public static final int REFUND_ORDER_DETAIL_CONFLICT = 607;
	public static final String REFUND_ORDER_DETAIL_CONFLICT_DESC = "退款订单已经存在，但是重复提交的明细不一致";

	/* 608待退款金额超过订单实际支付金额 */
	public static final int REFUND_ORDER_AMOUNT_NOT_ENOUGH = 608;
	public static final String REFUND_ORDER_AMOUNT_NOT_ENOUGH_DESC = "待退款金额超过订单实际支付金额";

	/* 609充值单状态异常，不容许退款 */
	public static final int CHARGE_CAN_NOT_REFUND = 609;
	public static final String CHARGE_CAN_NOT_REFUND_DESC = "充值单状态异常，不容许退款";

	/* 610充值单已经存在，但是重复提交的明细不一致 */
	public static final int CHARGE_REFUND_DETAIL_CONFLICT = 610;
	public static final String CHARGE_REFUND_DETAIL_CONFLICT_DESC = "充值单已经存在，但是重复提交的明细不一致";

	/* 611待退款金额超过订单实际支付金额 */
	public static final int CHARGE_AMOUNT_NOT_ENOUGH = 611;
	public static final String CHARGE_AMOUNT_NOT_ENOUGH_DESC = "待退款金额超过订单实际支付金额";

	/* 612退款值网易宝记录存在，但是重复提交的明细不一致 */
	public static final int REFUND_EPAY_TRANSFER_DETAIL_CONFLICT = 612;
	public static final String REFUND_EPAY_TRANSFER_DETAIL_CONFLICT_DESC = "退款值网易宝记录存在，但是重复提交的明细不一致";

	/* 613请求微信接口异常 */
	public static final int FAIL_BANK = 613;
	public static final String FAIL_BANK_DESC = "请求微信接口异常";

	/* 619提现前请先绑定银行卡 */
	public static final int WITHDRAW_BANKCARD_NOT_BIND_ERROR = 619;
	public static final String WITHDRAW_BANKCARD_NOT_BIND_ERROR_DESC = "提现前请先绑定银行卡";

	/* 615提现金额过少 */
	public static final int WITHDRAW_AMOUNT_ERROR = 615;
	public static final String WITHDRAW_AMOUNT_ERROR_DESC = "提现金额过少";

	/* 616今天提现次数过多，请明天再试 */
	public static final int WITHDRAW_DAILY_COUNT_ERROR = 616;
	public static final String WITHDRAW_DAILY_COUNT_ERROR_DESC = "今天提现次数过多，请明天再试";

	/* 618提现余额不足 */
	public static final int WITHDRAW_BALANCE_NOT_ENOUGH_ERROR = 618;
	public static final String WITHDRAW_BALANCE_NOT_ENOUGH_ERROR_DESC = "提现余额不足";

	/* 617余额不足 */
	public static final int BALANCE_NOT_ENOUGH_ERROR = 617;
	public static final String BALANCE_NOT_ENOUGH_ERROR_DESC = "余额不足";

	/* 620您账户的余额不足，当前订单已关闭，请重新投注 */
	public static final int BALANCE_NOT_ENOUGH_CLOSE_ERROR = 620;
	public static final String BALANCE_NOT_ENOUGH_CLOSE_ERROR_DESC = "您账户的余额不足，当前订单已关闭，请重新投注";

	/* 614已绑定过银行卡 */
	public static final int HAS_BIND_BANK_CARD = 614;
	public static final String HAS_BIND_BANK_CARD_DESC = "已绑定过银行卡";

	/* 621您还未进行身份验证 */
	public static final int HAS_NOT_VERIFY_IDENTITY = 621;
	public static final String HAS_NOT_VERIFY_IDENTITY_DESC = "您还未进行身份验证";
	
}
