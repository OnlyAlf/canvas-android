// Copyright (C) 2019 - present Instructure, Inc.
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, version 3 of the License.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

import 'package:flutter/material.dart';
import 'package:flutter_parent/screens/courses/details/course_details_model.dart';
import 'package:flutter_parent/screens/under_construction_screen.dart';
import 'package:provider/provider.dart';

class CourseSummaryScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Consumer<CourseDetailsModel>(
      builder: (context, model, _) =>
          RefreshIndicator(onRefresh: () => model.loadData(refreshSummaryList: true), child: UnderConstructionScreen()),
    );
  }
}
