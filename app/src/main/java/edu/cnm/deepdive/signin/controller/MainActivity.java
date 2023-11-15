package edu.cnm.deepdive.signin.controller;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.signin.R;
import edu.cnm.deepdive.signin.databinding.ActivityMainBinding;
import edu.cnm.deepdive.signin.viewmodel.LoginViewModel;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;
  private LoginViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupTransitions();
    setupUI();
    setupViewModel();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.main_options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled;
    if (item.getItemId() == R.id.sign_out) {
      viewModel.signOut();
      handled = true;
    } else {
      handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  private void setupTransitions() {
    getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
    getWindow().setEnterTransition(new Slide(Gravity.START));
    getWindow().setExitTransition(new Slide(Gravity.START));
  }

  private void setupUI() {
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    binding.clientId.setText(getText(R.string.service_client_id));
    binding.namespace.setText(getPackageName());
  }

  private void setupViewModel() {
    viewModel = new ViewModelProvider(this)
        .get(LoginViewModel.class);
    getLifecycle().addObserver(viewModel);
    viewModel
        .getAccount()
        .observe(this, this::handleAccount);
  }

  private void handleAccount(GoogleSignInAccount account) {
    if (account != null) {
      binding.displayName.setText(account.getDisplayName());
      if (account.getIdToken() != null) {
        binding.bearerToken.setText(account.getIdToken());
      } else {
        binding.bearerToken.setVisibility(View.GONE);
      }
    } else {
      Intent intent = new Intent(this, LoginActivity.class)
          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      //noinspection unchecked
      startActivity(
          intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }
  }

}