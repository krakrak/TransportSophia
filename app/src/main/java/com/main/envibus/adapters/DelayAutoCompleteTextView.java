package com.main.envibus.adapters;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import android.os.Handler;

/**
 * Created by KraKk on 21/12/2015.
 */
public class DelayAutoCompleteTextView extends AutoCompleteTextView
{
	private static final int MESSAGE_TEXT_CHANGED = 100;
	private static final int DEFAULT_AUTOCOMPLETE_DELAY = 750;

	private int autoCompleteDelay = DEFAULT_AUTOCOMPLETE_DELAY;
	private ProgressBar loadingIndicator;

	private final Handler handler = new Handler()
	{
		@Override
		public void handleMessage (Message msg)
		{
			DelayAutoCompleteTextView.super.performFiltering((CharSequence) msg.obj, msg.arg1);
		}
	};
	public DelayAutoCompleteTextView (Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public void setLoadingIndicator(ProgressBar progressBar)
	{
		this.loadingIndicator = progressBar;
	}

	public void setAutoCompleteDelay (int autoCompleteDelay)
	{
		this.autoCompleteDelay = autoCompleteDelay;
	}

	@Override
	protected void performFiltering(CharSequence text, int keyCode)
	{
		if (loadingIndicator != null)
		{
			loadingIndicator.setVisibility(View.VISIBLE);
		}

		handler.removeMessages(MESSAGE_TEXT_CHANGED);
		handler.sendMessageDelayed(handler.obtainMessage(MESSAGE_TEXT_CHANGED, text), autoCompleteDelay);
	}

	public void onFilterComplete(int count)
	{
		if(loadingIndicator != null)
		{
			loadingIndicator.setVisibility(View.GONE);
		}

		super.onFilterComplete(count);
	}
}
