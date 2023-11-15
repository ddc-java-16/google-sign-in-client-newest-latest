package edu.cnm.deepdive.signin.controller;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.signin.R;
import edu.cnm.deepdive.signin.databinding.ActivityLoginBinding;
import edu.cnm.deepdive.signin.viewmodel.LoginViewModel;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

  private ActivityLoginBinding binding;
  private LoginViewModel viewModel;
  private ActivityResultLauncher<Intent> launcher;
  private boolean uiLoaded;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupTransitions();
    setupViewModel();
  }

  private void setupTransitions() {
    getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
    getWindow().setEnterTransition(new Slide(Gravity.START));
    getWindow().setExitTransition(new Slide(Gravity.START));
  }

  private void setupViewModel() {
    viewModel = new ViewModelProvider(this)
        .get(LoginViewModel.class);
    getLifecycle().addObserver(viewModel);
    viewModel
        .getAccount()
        .observe(this, this::handleAccount);
    viewModel
        .getThrowable()
        .observe(this, this::informFailure);
    launcher = registerForActivityResult(new StartActivityForResult(), viewModel::completeSignIn);
  }

  private void handleAccount(GoogleSignInAccount account) {
    if (account != null) {
      Intent intent = new Intent(this, MainActivity.class)
          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      //noinspection unchecked
      startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    } else if (!uiLoaded) {
      setupUI();
    }
  }

  private void setupUI() {
    binding = ActivityLoginBinding.inflate(getLayoutInflater());
    binding.signIn.setOnClickListener((v) -> viewModel.startSignIn(launcher));
    setContentView(binding.getRoot());
    uiLoaded = true;
  }

  private void informFailure(Throwable throwable) {
    if (throwable != null) {
      Snackbar.make(binding.getRoot(), R.string.login_failure_message, Snackbar.LENGTH_LONG)
          .show();
    }
  }

}