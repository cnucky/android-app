package net.ivpn.client.ui.signup;

import android.content.Context;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import net.ivpn.client.IVPNApplication;
import net.ivpn.client.R;
import net.ivpn.client.common.billing.BillingManagerWrapper;
import net.ivpn.client.common.prefs.ServersRepository;
import net.ivpn.client.common.prefs.Settings;
import net.ivpn.client.common.prefs.UserPreference;
import net.ivpn.client.common.utils.StringUtil;
import net.ivpn.client.rest.HttpClientFactory;
import net.ivpn.client.rest.RequestListener;
import net.ivpn.client.rest.Responses;
import net.ivpn.client.rest.data.subscription.ValidateAccountRequestBody;
import net.ivpn.client.rest.data.subscription.ValidateAccountResponse;
import net.ivpn.client.rest.requests.common.Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import javax.inject.Inject;

public class SignUpViewModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignUpViewModel.class);

    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public final ObservableField<String> emailError = new ObservableField<>();
    public final ObservableField<String> passwordError = new ObservableField<>();
    public final ObservableBoolean dataLoading = new ObservableBoolean();

    private SignUpNavigator navigator;
    private BillingManagerWrapper billingManagerWrapper;
    private Request<ValidateAccountResponse> request;
    private Context context;

    @Inject
    SignUpViewModel(BillingManagerWrapper billingManagerWrapper,
                    Settings settings, HttpClientFactory httpClientFactory, ServersRepository serversRepository) {
        this.billingManagerWrapper = billingManagerWrapper;

        request = new Request<>(settings, httpClientFactory, serversRepository, Request.Duration.SHORT);
        context = IVPNApplication.getApplication();
    }

    void signUp() {
        if (password.get() == null || Objects.requireNonNull(password.get()).isEmpty()) {
            passwordError.set(context.getString(R.string.sign_up_no_password_error));
            return;
        }
        if (StringUtil.validateUsername(email.get())) {
            validateAccount(email.get(), password.get());
        } else {
            emailError.set(context.getString(R.string.sign_up_wrong_email_format));
        }
    }

    public void setNavigator(SignUpNavigator navigator) {
        this.navigator = navigator;
    }

    private void validateAccount(String email, String password) {
        LOGGER.info("Validate");
        dataLoading.set(true);
        resetErrors();
        ValidateAccountRequestBody body = new ValidateAccountRequestBody(email, password);
        //ToDo remove it
//        LOGGER.info("ValidateAccountRequestBody = " + body);
        request.start(api -> api.validateAccount(body), new RequestListener<ValidateAccountResponse>() {
            @Override
            public void onSuccess(ValidateAccountResponse response) {
                LOGGER.info("ValidateAccountResponse = " + response);
                dataLoading.set(false);
                if (response.getStatus() == Responses.SUCCESS) {
                    saveCredentials(email, password);
                    navigator.onSignUp();
                } else {
                    if (response.getMessage() != null && (response.getMessage().contains("password")
                            || response.getMessage().contains("Password"))) {
                        passwordError.set(response.getMessage());
                    } else {
                        navigator.onError(String.valueOf(response.getStatus()), response.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                //ToDo refactor it
                dataLoading.set(false);
                LOGGER.error(throwable.getMessage());
                navigator.onError("", context.getString(R.string.sign_up_unknown_error));
            }

            @Override
            public void onError(String error) {
                dataLoading.set(false);
                LOGGER.error(error);
                navigator.onError("", error);
            }
        });
    }

    private void resetErrors() {
        emailError.set(null);
        passwordError.set(null);
    }

    private void saveCredentials(String email, String password) {
        if (password == null || password.isEmpty()) {
            return;
        }

        billingManagerWrapper.setEmail(email);
        billingManagerWrapper.setPassword(password);
    }
}