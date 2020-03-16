/**
 * Copyright (c) Istituto Nazionale di Fisica Nucleare (INFN). 2016-2019
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.infn.mw.iam.core.userinfo;

import static it.infn.mw.iam.core.oauth.profile.aarc.AarcJWTProfile.AARC_OIDC_CLAIM_AFFILIATION;
import static it.infn.mw.iam.core.oauth.profile.aarc.AarcJWTProfile.AARC_OIDC_CLAIM_ENTITLEMENT;

import java.util.Set;

import org.mitre.openid.connect.model.UserInfo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class AarcDecoratedUserInfo extends DelegateUserInfoAdapter implements AarcUserInfo {

  private static final long serialVersionUID = 1L;

  private String scopedAffiliation;
  private Set<String> entitlements;

  public AarcDecoratedUserInfo(UserInfo delegate) {
    super(delegate);
  }

  @Override
  public JsonObject toJson() {
    JsonObject json = super.toJson();

    json.remove("groups");
    json.remove("organisation_name");

    json.add(AARC_OIDC_CLAIM_AFFILIATION, new JsonPrimitive(scopedAffiliation));

    JsonArray urns = new JsonArray();
    entitlements.forEach(urn -> urns.add(new JsonPrimitive(urn)));
    json.add(AARC_OIDC_CLAIM_ENTITLEMENT, urns);

    return json;
  }

  @Override
  public String getScopedAffiliation() {
    return scopedAffiliation;
  }

  @Override
  public void setScopedAffiliation(String scopedAffiliation) {
    this.scopedAffiliation = scopedAffiliation;
  }

  @Override
  public Set<String> getEntitlements() {
    return entitlements;
  }

  @Override
  public void setEntitlements(Set<String> entitlements) {
    this.entitlements = entitlements; 
  }

  public static AarcDecoratedUserInfo forUser(UserInfo u) {
    return new AarcDecoratedUserInfo(u);
  }
}
